package uitests;

import core.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

/**
 * UI Functional Suite: Authentication Layer.
 * Validates the core login flow using a standalone TestNG approach.
 * Serves as a fast-feedback mechanism for critical path verification.
 */
public class LoginUiTests {

    private LoginPage loginPage;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/login";

    /**
     * Environment setup and session initialization.
     * Guarantees a fresh browser context for each test execution to eliminate side effects.
     */
    @BeforeMethod
    public void setup() {
        // Enforcing browser binary management via centralized factory
        DriverFactory.setupDriver("chrome");

        // Direct navigation to the system under test (SUT) entry point
        DriverFactory.getDriver().get(BASE_URL);
        
        // Initializing the Page Object to abstract DOM interactions
        loginPage = new LoginPage(DriverFactory.getDriver());
    }

    /**
     * Positive Scenario: Successful Authentication.
     * Verifies that valid credentials grant access to the protected zone 
     * and trigger the appropriate system feedback (Flash Message).
     */
    @Test(description = "Verify successful login using Page Object Model")
    public void testSuccessfulLogin() {
        // Test Data: Sensitive information would typically be externalized in a real-world scenario
        final String USERNAME = "tomsmith";
        final String PASSWORD = "SuperSecretPassword!";
        final String EXPECTED_MESSAGE_PART = "You logged into a secure area!";

        /**
         * Execution: Delegating the business flow to the Page Object.
         * This ensures the test script remains clean and focused on outcomes, not selectors.
         */
        loginPage.login(USERNAME, PASSWORD);

        /**
         * Validation: Cross-referencing system state with expected business outcome.
         * Using soft/hard assertions to provide clear diagnostic data upon failure.
         */
        String actualMessage = loginPage.getFlashMessageText();

        Assert.assertTrue(actualMessage.contains(EXPECTED_MESSAGE_PART),
                "Business Logic Failure: The expected success banner was not displayed. " +
                "Expected: [" + EXPECTED_MESSAGE_PART + "] | Actual: [" + actualMessage + "]");

        logInfo("Authentication verified successfully.");
    }

    /**
     * Post-condition: Reliable session termination.
     * Prevents infrastructure overhead by ensuring the driver process is killed after execution.
     */
    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    /**
     * Helper for standardized logging within the test suite.
     */
    private void logInfo(String message) {
        System.out.println("TEST STATUS: " + message);
    }
}
