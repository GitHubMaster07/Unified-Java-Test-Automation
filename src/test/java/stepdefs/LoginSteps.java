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

/**
 * Step Definitions for User Authentication scenarios.
 * Maps Gherkin business logic to the Page Object Model, managing state validation
 * and explicit synchronization with the browser.
 */
public class LoginSteps {
    private final LoginPage loginPage;
    private final WebDriverWait wait;

    /**
     * Dependency injection via thread-safe WebDriver orchestration.
     * Initializes the operational context for the current scenario execution.
     */
    public LoginSteps() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
    }

    /**
     * Verification of the initial entry point.
     * Confirms the environment is correctly pre-conditioned by the lifecycle hooks 
     * before proceeding with the interaction flow.
     */
    @Given("the user is on the Login page")
    public void theUserIsOnTheLoginPage() {
        // Asserting the landing state to ensure test stability
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login"), "System integrity check: Not on the expected entry page.");
    }

    /**
     * Execution of the primary business transaction.
     * Orchestrates the credential injection and submission process.
     */
    @When("the user enters the username {string} and password {string}")
    public void theUserEntersTheUsernameAndPassword(String username, String password) {
        loginPage.login(username, password);
    }

    /**
     * Syntactic sugar for BDD readability.
     * Maintains the narrative flow of the Feature file while ensuring 
     * atomic action consistency within the underlying page object.
     */
    @And("the user clicks the Login button")
    public void theUserClicksTheLoginButton() {
        // Implementation logic encapsulated within the composite login action
    }

    /**
     * Post-condition verification with explicit synchronization.
     * Validates successful transition to the authorized zone, handling 
     * asynchronous navigation delays through a fail-fast wait strategy.
     */
    @Then("the user should be redirected to the secure area")
    public void theUserShouldBeRedirectedToTheSecureArea() {
        final String EXPECTED_URL_PART = "/secure";

        try {
            /**
             * Polling for the expected state transition. 
             * Ensures the test account for network latency and backend processing time.
             */
            wait.until(ExpectedConditions.urlContains(EXPECTED_URL_PART));

            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            Assert.assertTrue(currentUrl.contains(EXPECTED_URL_PART),
                    "Security Breach/Logic Error: Expected redirection to secure area failed. Terminal URL: " + currentUrl);

        } catch (Exception e) {
            /**
             * Critical failure handling. 
             * Provides detailed diagnostic output for immediate triage of automation failures.
             */
            Assert.fail("Automation Triage: Redirection failed during the timeout period. Exception: " + e.getMessage());
        }
    }
}
