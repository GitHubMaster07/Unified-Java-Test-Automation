package stepdefs;

import core.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.LoginPage;

import java.time.Duration;

public class LoginSteps {
    private final LoginPage loginPage;
    private final WebDriverWait wait;

    /**
     * Constructor initializes page object and wait object using the thread-safe
     * WebDriver instance retrieved from the DriverFactory.
     */
    public LoginSteps() {
        // DriverFactory.getDriver() retrieves the WebDriver instance managed by the Hooks.
        loginPage = new LoginPage(DriverFactory.getDriver());
        wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
    }

    /**
     * GIVEN: Confirms the user is on the login page.
     * Navigation is handled by the @Before hook in Hooks.java
     */
    @Given("the user is on the Login page")
    public void theUserIsOnTheLoginPage() {
        // This step is intentionally empty. Navigation is managed by the @Before hook in Hooks.java
        // which uses the BASE_URL to load the login page before this step executes.
        System.out.println("GIVEN step confirmed: Browser is already navigated to the Login page via Hooks.");
    }

    /**
     * WHEN: Enters credentials and clicks the login button.
     */
    @When("the user enters the username {string} and password {string}")
    public void theUserEntersTheUsernameAndPassword(String username, String password) {
        loginPage.login(username, password);
    }

    /**
     * AND: Explicit step for clicking the button, kept for BDD clarity.
     */
    @And("the user clicks the Login button")
    public void theUserClicksTheLoginButton() {
        // This action is implicitly handled within the preceding @When step (loginPage.login),
        // but the step is kept for BDD clarity and alignment with the feature file.
    }

    /**
     * THEN: Verifies redirection to the secure area.
     */
    @Then("the user should be redirected to the secure area")
    public void theUserShouldBeRedirectedToTheSecureArea() {
        final String EXPECTED_URL_PART = "/secure";

        try {
            // Wait for the URL to contain the expected secure path (for successful login)
            wait.until(ExpectedConditions.urlContains(EXPECTED_URL_PART));

            String currentUrl = DriverFactory.getDriver().getCurrentUrl();

            Assert.assertTrue(currentUrl.contains(EXPECTED_URL_PART),
                    "Verification Failed: Not redirected to the secure area. Current URL: " + currentUrl);

            System.out.println("Successfully redirected to Secure Area!");

        } catch (Exception e) {
            System.err.println("Redirection verification failed: " + e.getMessage());
            Assert.fail("Verification Failed: Redirection failed. Current URL: " + DriverFactory.getDriver().getCurrentUrl() + ". Exception: " + e.getMessage());
        }
    }
}