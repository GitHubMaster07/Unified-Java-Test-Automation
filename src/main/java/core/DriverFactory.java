package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class DriverFactory {
    private static WebDriver driver;
    // Note: It's better to move BASE_URL to environment.properties later
    public static final String BASE_URL = "https://the-internet.herokuapp.com/login";

    public static void setupDriver(String browser) {
        if (driver == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions options = new ChromeOptions();

                    // Check if headless mode is requested via System Property (e.g., from Maven/CI)
                    boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

                    if (isHeadless) {
                        options.addArguments("--headless=new"); // New headless mode for Chrome
                        options.addArguments("--disable-gpu");
                        options.addArguments("--window-size=1920,1080");
                        options.addArguments("--no-sandbox");
                        options.addArguments("--disable-dev-shm-usage");
                    }

                    driver = new ChromeDriver(options);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            // Maximize only if not in headless (headless size is set via arguments)
            if (!Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                driver.manage().window().maximize();
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}