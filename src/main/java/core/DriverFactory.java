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

                    // Detect environment: Auto-enable headless mode if running in GitHub Actions (CI=true)
                    // or if explicitly set to 'true' in config.properties.
                    boolean isHeadless = Boolean.parseBoolean(ConfigManager.getProperty("headless", "false"))
                            || System.getenv("CI") != null;

                    if (isHeadless) {
                        // '--headless=new' is mandatory for stable execution in Linux/CI environments without a GUI.
                        // '--no-sandbox' and '--disable-dev-shm-usage' prevent memory crashes in containerized runners.
                        options.addArguments("--headless=new");
                        options.addArguments("--no-sandbox");
                        options.addArguments("--disable-dev-shm-usage");
                        options.addArguments("--window-size=1920,1080");
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
     * orphaned processes and memory leaks in long-running CI loops.
     */
    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}