package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class DriverFactory {

    // ThreadLocal prevents race conditions during parallel execution in TestNG.
    // Each thread maintains its own isolated browser session to avoid shared state corruption.
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void setupDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            WebDriver driver;

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();

                    boolean isHeadless = Boolean.parseBoolean(ConfigManager.getProperty("headless", "false"));

                    if (isHeadless) {
                        // '--headless=new' is used to resolve rendering inconsistencies found in the legacy engine.
                        // '--no-sandbox' is mandatory for stable execution within Docker/Linux containers.
                        options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--window-size=1920,1080");
                    } else {
                        options.addArguments("--start-maximized");
                    }

                    driver = new ChromeDriver(options);
                    break;
                default:
                    throw new RuntimeException("Unsupported browser: " + browser);
            }

            // Syncing implicit wait with global config to handle network latency across environments.
            int timeout = ConfigManager.getIntProperty("timeout", 10);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));

            driverThreadLocal.set(driver);
        }
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Terminating the session and clearing the reference is required to prevent
     * orphaned 'chromedriver.exe' processes and memory leaks in long-running CI loops.
     */
    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}