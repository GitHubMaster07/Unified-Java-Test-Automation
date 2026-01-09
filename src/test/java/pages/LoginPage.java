package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;

    // Using stable IDs where possible. For the login button, a specific CSS hierarchy
    // is required to avoid collision with other 'submit' buttons on the landing page.
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("#login button[type='submit']");
    private final By secureAreaFlashMessage = By.id("flash");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        // Simple click is often enough, but if the UI gets laggy,
        // we might need an explicit 'elementToBeClickable' wait here.
        driver.findElement(loginButton).click();
    }

    /**
     * Orchestrating the full auth sequence.
     * Decoupling the login logic from the test steps ensures that changes in
     * the auth flow (e.g., adding a 2FA step) only require a single fix here.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getFlashMessageText() {
        // Flash messages are often dynamic or time-sensitive (fade out).
        // We fetch the text immediately to avoid 'StaleElementReferenceException'.
        return driver.findElement(secureAreaFlashMessage).getText();
    }
}