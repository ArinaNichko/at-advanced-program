package org.example.pages;

import org.example.setUp.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.example.utils.WaitUtils.getDriverWait;

public class LoginPage extends BasePage {
    private static String baseUrl = propertiesHelper.getProperty("baseUrl");

    @FindBy(xpath = "//input[@placeholder='Login']")
    private WebElement loginPlaceholder;
    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordPlaceholder;
    @FindBy(xpath = "//div[contains(@class, 'login-button')]/button")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterLogin(String login) {
        loginPlaceholder.sendKeys(login);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordPlaceholder.sendKeys(password);
        return this;
    }

    public void clickLogin() {
        getDriverWait().until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        new DashboardPage(driver);
    }

    public LoginPage goToLoginPage() {
        driver.get(baseUrl);
        return this;
    }

}
