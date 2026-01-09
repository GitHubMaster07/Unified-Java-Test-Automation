package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Core orchestration layer for UI automation.
 * Manages WebDriver lifecycle with thread-safe execution to support high-concurrency testing.
 */
public class BaseUITest {

    protected static final Logger log = LogManager.getLogger(BaseUITest.class);
    
    /**
     * Ensures isolation between parallel test threads. 
     * This prevents cross-contamination of browser sessions during massive regression runs.
     */
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Pre-test synchronization and environment preparation.
     * Automatically handles binary management and driver injection with enforced synchronization policies.
     */
    @BeforeMethod(alwaysRun = true)
    protected void setupBrowser() {
        log.info("Initializing automated test session.");
        
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        /**
         * Fetching dynamic timeout from global configuration.
         * Using a 'Fail-Safe' approach: the framework ensures stability by applying a 10s default 
         * even if external configuration is corrupted or missing.
         */
        String timeoutValue = ConfigManager.getProperty("ui.timeout.seconds");
        int timeout = 10; 

        try {
            if (timeoutValue != null && !timeoutValue.trim().isEmpty()) {
                timeout = Integer.parseInt(timeoutValue.trim());
            } else {
                log.warn("Performance constraint 'ui.timeout.seconds' missing. Using conservative 10s default.");
            }
        } catch (NumberFormatException e) {
            log.error("Corrupted configuration for timeout '{}'. Reverting to safe default.", timeoutValue);
        }

        // Applying global implicit wait for standard element synchronization
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();

        driverThreadLocal.set(driver);
        log.info("Test environment ready for thread ID: {}", Thread.currentThread().getId());
    }

    /**
     * Graceful resource decommissioning.
     * Guarantees all browser processes are terminated and memory is released, 
     * preventing resource leaks on CI/CD agents.
     */
    @AfterMethod(alwaysRun = true)
    protected void tearDownBrowser() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            log.info("Cleaning up session and releasing browser resources.");
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    /**
     * Provides the active driver context for the current scenario.
     * @return Thread-specific WebDriver instance.
     */
    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }
}
