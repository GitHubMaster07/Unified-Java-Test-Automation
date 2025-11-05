package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseUITest {

    protected static final Logger log = LogManager.getLogger(BaseUITest.class);
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Protected method to initialize the WebDriver
    @BeforeMethod(alwaysRun = true)
    protected void setupBrowser() {
        log.info("Setting up WebDriver for the current thread.");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // --- START IMPROVED CONFIG READING (Line 27 area) ---
        String timeoutValue = ConfigManager.getProperty("ui.timeout.seconds");
        int timeout = 10; // Default implicit wait to 10 seconds for robustness

        try {
            if (timeoutValue != null && !timeoutValue.trim().isEmpty()) {
                timeout = Integer.parseInt(timeoutValue.trim());
            } else {
                log.warn("Property 'ui.timeout.seconds' not found or empty. Defaulting to {} seconds.", timeout);
            }
        } catch (NumberFormatException e) {
            // Log the error but continue with the default timeout
            log.error("Invalid number format for 'ui.timeout.seconds'='{}'. Defaulting to 10 seconds.", timeoutValue, e);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        // --- END IMPROVED CONFIG READING ---

        driver.manage().window().maximize();

        driverThreadLocal.set(driver);
        log.info("WebDriver initialized and stored in ThreadLocal.");
    }

    // Protected method to clean up the WebDriver
    @AfterMethod(alwaysRun = true)
    protected void tearDownBrowser() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            log.info("Quitting WebDriver for the current thread.");
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    // Public getter method for the driver instance
    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }
}