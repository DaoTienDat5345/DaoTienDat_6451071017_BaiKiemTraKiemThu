package e2e.pages;

import e2e.base.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    public static final String URL = "https://vanphongdientu.utc.edu.vn/Login";

    // Locators
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("userpwd");
    private final By loginButton = By.cssSelector("input.submit_login");
    private final By errorMessage = By.cssSelector(".error, .alert, .message, .notification");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(URL);
        return this;
    }

    public void enterUsername(String username) {
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    /**
     * Thuc hien dang nhap voi username va password
     */
    public void loginAs(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    /**
     * Thuc hien dang nhap va ky vong that bai (van o trang login)
     */
    public LoginPage loginExpectingFailure(String username, String password) {
        loginAs(username, password);
        return this;
    }

    /**
     * Kiem tra co dang o trang login khong
     */
    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("/Login");
    }

    /**
     * Lay title cua trang
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Kiem tra co alert JavaScript khong
     */
    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    /**
     * Lay text tu alert JavaScript
     */
    public String getAlertText() {
        try {
            Alert alert = driver.switchTo().alert();
            String text = alert.getText();
            alert.accept();
            return text;
        } catch (NoAlertPresentException e) {
            return "";
        }
    }

    /**
     * Kiem tra co thong bao loi khong
     */
    public boolean isErrorDisplayed() {
        try {
            return driver.findElement(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Lay noi dung thong bao loi
     */
    public String getErrorMessageText() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Kiem tra truong username co hien thi khong
     */
    public boolean isUsernameFieldDisplayed() {
        try {
            return driver.findElement(usernameField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Kiem tra truong password co hien thi khong
     */
    public boolean isPasswordFieldDisplayed() {
        try {
            return driver.findElement(passwordField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Kiem tra nut login co hien thi khong
     */
    public boolean isLoginButtonDisplayed() {
        try {
            return driver.findElement(loginButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Lay gia tri placeholder cua truong username
     */
    public String getUsernamePlaceholder() {
        return driver.findElement(usernameField).getAttribute("placeholder");
    }

    /**
     * Lay gia tri placeholder cua truong password
     */
    public String getPasswordPlaceholder() {
        return driver.findElement(passwordField).getAttribute("placeholder");
    }

    /**
     * Lay URL hien tai
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Kiem tra truong username co rong khong
     */
    public boolean isUsernameEmpty() {
        return driver.findElement(usernameField).getAttribute("value").isEmpty();
    }

    /**
     * Kiem tra truong password co rong khong
     */
    public boolean isPasswordEmpty() {
        return driver.findElement(passwordField).getAttribute("value").isEmpty();
    }
}
