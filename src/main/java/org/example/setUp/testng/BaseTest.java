package org.example.setUp.testng;

import org.example.driver.DriverProvider;
import org.example.pages.LoginPage;
import org.example.utils.PropertiesHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {
    public WebDriver driver;
    public static PropertiesHelper propertiesHelper;
    public static String login;
    public static String password;

    @Parameters({"browser"})
    @BeforeMethod
    public void beforeClass(String browser) {
        DriverProvider.initialize(browser);
        driver = DriverProvider.getInstance();
        PageFactory.initElements(driver, this);
        initializeConstants();
        login();
    }

    @AfterMethod
    public void afterClass() {
        DriverProvider.getInstance().quit();
        DriverProvider.removeInstance();
    }

    public void initializeConstants() {
        login = propertiesHelper.getProperty("login");
        password = propertiesHelper.getProperty("password");
    }

    public void login() {
        new LoginPage(driver)
                .goToLoginPage()
                .enterLogin(login)
                .enterPassword(password)
                .clickLogin();
    }
}
