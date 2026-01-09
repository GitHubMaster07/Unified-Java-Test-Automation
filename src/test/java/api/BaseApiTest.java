package api;

import core.ConfigManager;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class BaseApiTest {

    @BeforeSuite(alwaysRun = true)
    public void globalApiSetup() {
        // We pull the URI once at the suite level to ensure environment consistency.
        // If this value is null, RestAssured defaults to localhost, which causes
        // false positives or confusing connection refused errors in CI.
        String baseUri = ConfigManager.getProperty("api.base.url");

        if (baseUri != null) {
            RestAssured.baseURI = baseUri;
            // Visible confirmation in logs to distinguish between Dev, QA, and Prod runs
            // before any actual requests are fired.
            System.out.println("FRAMEWORK BOOT: Base URI set to: " + baseUri);
        } else {
            // Crashing early here prevents the entire suite from running against a non-existent target.
            throw new RuntimeException("Fatal: 'api.base.url' missing in config. Initialization aborted.");
        }
    }
}