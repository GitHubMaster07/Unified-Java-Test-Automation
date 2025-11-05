package stepdefs;

import core.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber Hooks class — runs before and after each scenario.
 * Responsible for WebDriver setup and teardown.
 */
public class Hooks {

    private WebDriver driver;

    @Before
    public void setup() {
        // Initialize browser (can change to "firefox" or "edge")
        DriverFactory.setupDriver("chrome");

        // Retrieve driver instance
        driver = DriverFactory.getDriver();

        // Navigate to the base URL
        driver.get(DriverFactory.BASE_URL);
    }

    @After
    public void tearDown() {
        // Close and quit the browser after each scenario
        DriverFactory.quitDriver();
    }
}
