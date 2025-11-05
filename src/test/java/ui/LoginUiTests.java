package ui;

import core.BaseUITest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

/**
 * UI tests for the login functionality, demonstrating the Page Object Model (POM).
 */
public class LoginUiTests extends BaseUITest {

    // Valid credentials for the test site
    private static final String VALID_USER = "tomsmith";
    private static final String VALID_PASS = "SuperSecretPassword!";

    @Test(groups = {"ui", "smoke"})
    public void testSuccessfulLogin() {
        logger.info("Starting testSuccessfulLogin UI test.");

        // Initialize the Page Object using the driver from BaseUITest
        LoginPage loginPage = new LoginPage(getDriver());

        // Perform login action
        loginPage.login(VALID_USER, VALID_PASS);

        // Assert the success message
        String expectedMessage = "You logged into a secure area!";
        String actualMessage = loginPage.getFlashMessageText();

        // The actual message includes an 'x' button text, so we check for containment
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Expected success message was not displayed. Actual: " + actualMessage);

        logger.info("Successfully logged in and validated secure area message.");
    }

    @Test(groups = {"ui", "regression"})
    public void testInvalidPasswordLogin() {
        logger.info("Starting testInvalidPasswordLogin UI test.");

        LoginPage loginPage = new LoginPage(getDriver());

        // Use a valid username but wrong password
        loginPage.login(VALID_USER, "WrongPassword");

        // Assert the failure message
        String expectedMessage = "Your password is invalid!";
        String actualMessage = loginPage.getFlashMessageText();

        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Expected failure message was not displayed. Actual: " + actualMessage);

        logger.info("Successfully failed login and validated error message.");
    }
}
