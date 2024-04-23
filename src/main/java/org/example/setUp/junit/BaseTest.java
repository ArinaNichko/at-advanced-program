package org.example.setUp.junit;

import org.example.driver.DriverProvider;
import org.example.pages.LoginPage;
import org.example.utils.PropertiesHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BaseTest {
    public WebDriver driver;
    public PropertiesHelper propertiesHelper;
    public static String login;
    public static String password;

    @BeforeEach
    public void beforeClass() {
        propertiesHelper = PropertiesHelper.getInstance();
        DriverProvider.initialize("chrome");
        driver = DriverProvider.getInstance();
        PageFactory.initElements(driver, this);
        initializeConstants();
        login();
    }

    @AfterEach
    public void afterClass() {
        DriverProvider.getInstance().quit();
        DriverProvider.removeInstance();
    }

    public void initializeConstants() {
        login = propertiesHelper.getProperty("login");
        password = propertiesHelper.getProperty("password");
    }

    @BeforeEach
    public void login() {
        new LoginPage(driver)
                .goToLoginPage()
                .enterLogin(login)
                .enterPassword(password)
                .clickLogin();
    }
}
