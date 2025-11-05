package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Base class for all UI tests. Manages WebDriver initialization, ThreadLocal usage
 * for parallel execution, and cleanup, following the TestNG lifecycle.
 */
public class BaseUITest {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    protected static final Logger logger = LogManager.getLogger(BaseUITest.class);

    // Retrieve BASE_URL from configuration
    private static final String BASE_URL = ConfigManager.getProperty("ui.base.url");

    protected WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void setupBrowser() {
        logger.info("Setting up WebDriver for the current thread using browser: {}", ConfigManager.getProperty("ui.browser"));

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        // Retrieve implicit timeout from configuration
        int implicitTimeout = ConfigManager.getIntProperty("ui.timeout.seconds");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitTimeout));
        driver.manage().window().maximize();

        driverThreadLocal.set(driver);

        logger.info("WebDriver successfully initialized. Navigating to {}", BASE_URL);
        driver.get(BASE_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void teardownBrowser() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            logger.info("Closing and quitting WebDriver.");
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}