package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Model (POM) for the Secure Login page.
 * Encapsulates elements and methods for login interactions.
 */
public class LoginPage {

    private final WebDriver driver;

    // Locators
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(id = "flash")
    private WebElement flashMessage; // Used for success/error messages


    // Constructor: Initializes the WebDriver and PageFactory
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Executes the login action.
     * @param username The username to enter.
     * @param password The password to enter.
     * @return The current LoginPage object (or the next page object on success)
     */
    public LoginPage login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
        return this; // We stay on this page to check the flash message
    }

    /**
     * Retrieves the text of the flash message element.
     * @return The flash message string.
     */
    public String getFlashMessageText() {
        return flashMessage.getText();
    }
}
