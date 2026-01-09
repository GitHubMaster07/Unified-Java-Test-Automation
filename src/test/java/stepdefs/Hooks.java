package stepdefs;

import core.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

/**
 * Lifecycle Orchestrator for BDD Scenarios.
 * Manages the setup and teardown of the test environment to ensure cross-scenario isolation
 * and efficient resource management.
 */
public class Hooks {

    private WebDriver driver;

    /**
     * Environment Pre-Conditioning.
     * Initializes the driver instance based on the target execution profile 
     * and synchronizes the session with the application's base entry point.
     */
    @Before
    public void setup() {
        // Dynamic driver allocation (defaults to Chrome, extensible for multi-browser grids)
        DriverFactory.setupDriver("chrome");

        driver = DriverFactory.getDriver();

        // Establishing initial state for the test scenario
        driver.get(DriverFactory.BASE_URL);
    }

    /**
     * Post-Execution Resource Decommissioning.
     * Ensures the browser session is terminated and local resources are released 
     * regardless of the scenario outcome (passed/failed).
     */
    @After
    public void tearDown() {
        // Guarantees zero-leak policy for browser processes
        DriverFactory.quitDriver();
    }
}
