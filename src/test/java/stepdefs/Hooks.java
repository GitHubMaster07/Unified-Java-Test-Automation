package stepdefs;

import core.DriverFactory;
import core.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class Hooks {

    // Selective initialization: we only fire up the browser for UI/E2E tags
    // to avoid unnecessary overhead in pure API or Database test runs.
    @Before("@ui or @e2e")
    public void setUp() {
        String browser = ConfigManager.getProperty("browser", "chrome");
        DriverFactory.setupDriver(browser);

        String url = ConfigManager.getProperty("base.url");
        if (url != null) {
            // Ensuring the driver starts at the baseline URL to avoid 'blank page' errors on first steps.
            DriverFactory.getDriver().get(url);
        }
    }

    @After("@ui or @e2e")
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && DriverFactory.getDriver() != null) {
            // Capturing the DOM state at the exact moment of failure.
            // This is the only way to debug flaky elements in headless mode.
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            // Attaching to Cucumber and Allure simultaneously to ensure evidence
            // persists regardless of which reporting tool is used.
            scenario.attach(screenshot, "image/png", "Failure_Screenshot");
            Allure.addAttachment("Terminal State on Failure", new ByteArrayInputStream(screenshot));
        }

        // Mandatory teardown. If this fails on Windows, chromedriver.exe will hang,
        // eventually causing 'Out of Memory' on the CI agent.
        DriverFactory.quitDriver();
    }
}