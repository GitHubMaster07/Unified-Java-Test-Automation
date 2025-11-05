package uitests;

import core.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

/**
 * Standard TestNG test for UI login functionality.
 */
public class LoginUiTests {

    private LoginPage loginPage;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/login";

    @BeforeMethod
    public void setup() {
        // 1. Setup Driver
        DriverFactory.setupDriver("chrome");

        // 2. Navigate and initialize Page Object
        DriverFactory.getDriver().get(BASE_URL);
        loginPage = new LoginPage(DriverFactory.getDriver());
    }

    @Test(description = "Verify successful login using Page Object Model")
    public void testSuccessfulLogin() {
        final String USERNAME = "tomsmith";
        final String PASSWORD = "SuperSecretPassword!";
        final String EXPECTED_MESSAGE_PART = "You logged into a secure area!";

        // Action: Perform login
        loginPage.login(USERNAME, PASSWORD);

        // Assertion: Check for the success message
        String actualMessage = loginPage.getFlashMessageText();

        Assert.assertTrue(actualMessage.contains(EXPECTED_MESSAGE_PART),
                "Login failed! Expected message part: '" + EXPECTED_MESSAGE_PART +
                        "' but found: '" + actualMessage + "'");

        System.out.println("TestNG Login Success: " + actualMessage);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}