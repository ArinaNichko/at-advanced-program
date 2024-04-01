package org.example.setUp;

import org.example.driver.DriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {
    public WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod
    public void beforeClass(String browser) {
        DriverProvider.initialize(browser);
        driver = DriverProvider.getInstance();
        PageFactory.initElements(driver, this);
    }

    @AfterMethod
    public void afterClass() {
        DriverProvider.getInstance().quit();
        DriverProvider.removeInstance();
    }
}
