package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model for the Herokuapp Login Page.
 * Locators use reliable IDs and CSS Selectors.
 */
public class LoginPage {
    private final WebDriver driver;

    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    // Note: The button is often tricky, using a CSS selector for clarity.
    private final By loginButton = By.cssSelector("#login button[type='submit']");
    private final By secureAreaFlashMessage = By.id("flash");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Types the username into the field.
     */
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    /**
     * Types the password into the field.
     */
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    /**
     * Clicks the login button.
     */
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    /**
     * Combined action to perform a login attempt.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Retrieves the text of the success/failure message in the secure area.
     */
    public String getFlashMessageText() {
        return driver.findElement(secureAreaFlashMessage).getText();
    }
}