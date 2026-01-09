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

    public LoginSteps() {
        // Initializing page and wait here to keep individual steps clean from boilerplate setup.
        this.loginPage = new LoginPage(DriverFactory.getDriver());
        this.wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
    }

    @Given("the user is on the Login page")
    public void theUserIsOnTheLoginPage() {
        // Redundant navigation check: if the hook failed, this explicit get ensures the test starts at the right entry point.
        DriverFactory.getDriver().get("https://the-internet.herokuapp.com/login");

        // Waiting for URL ensures that slow redirects or JS-heavy page loads don't break the first 'sendKeys'.
        wait.until(ExpectedConditions.urlContains("login"));
    }

    @When("the user enters the username {string} and password {string}")
    public void theUserEntersTheUsernameAndPassword(String username, String password) {
        // Delegating to a composite page method to avoid cluttering the step with field-by-field logic.
        loginPage.login(username, password);
    }

    @And("the user clicks the Login button")
    public void theUserClicksTheLoginButton() {
        // No action needed if combined in @When, but kept as a placeholder for Gherkin readability.
    }

    @Then("the user should be redirected to the secure area")
    public void theUserShouldBeRedirectedToTheSecureArea() {
        final String EXPECTED_URL_PART = "/secure";

        try {
            // Explicit wait is the only way to account for backend latency and redirect time on CI.
            wait.until(ExpectedConditions.urlContains(EXPECTED_URL_PART));

            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            // Detailed failure message includes the actual URL to help identify 404s or unexpected error pages.
            Assert.assertTrue(currentUrl.contains(EXPECTED_URL_PART),
                    "Redirection mismatch. Found URL: " + currentUrl);

        } catch (Exception e) {
            // Re-throwing as an assertion failure ensures it's correctly flagged in Cucumber and Allure reports.
            Assert.fail("Timeout during redirection to secure area. Check server response time. Error: " + e.getMessage());
        }
    }
}