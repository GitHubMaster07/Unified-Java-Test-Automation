package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Abstraction of the Authentication Interface (Login Page).
 * Encapsulates UI locators and business-facing actions to decouple test logic 
 * from the underlying DOM structure.
 */
public class LoginPage {
    private final WebDriver driver;

    /**
     * Strategic Locators.
     * Prioritizing unique IDs for high performance and stability. 
     * CSS Selectors are utilized for complex button hierarchies where IDs are unavailable.
     */
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("#login button[type='submit']");
    private final By secureAreaFlashMessage = By.id("flash");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Populates the identity credential field.
     */
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    /**
     * Populates the security credential field.
     */
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    /**
     * Triggers the authentication transaction.
     */
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    /**
     * High-level business flow: User Authentication.
     * Consolidates individual atomic actions into a single operational unit 
     * to streamline test scenario development.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Accesses the operational feedback message (success/error).
     * Provides a point of verification for authentication outcomes.
     */
    public String getFlashMessageText() {
        return driver.findElement(secureAreaFlashMessage).getText();
    }
}
