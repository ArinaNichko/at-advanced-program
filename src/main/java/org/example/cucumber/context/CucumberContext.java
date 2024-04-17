package org.example.cucumber.context;

import org.example.driver.DriverProvider;
import org.example.pages.CreatedDashboardPage;
import org.example.pages.DashboardPage;
import org.example.pages.LoginPage;
import org.example.session.Session;
import org.example.session.SessionImpl;
import org.example.session.SessionKey;
import org.example.utils.PropertiesHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CucumberContext {
    protected static PropertiesHelper propertiesHelper = PropertiesHelper.getInstance();
    public WebDriver driver;
    public static String login;
    public static String password;
    public static LoginPage loginPage;
    public static DashboardPage dashboardPage;
    public static CreatedDashboardPage createdDashboardPage;
    public static final Session session = new SessionImpl();

    public void setUp() {
        DriverProvider.initialize("chrome");
        driver = DriverProvider.getInstance();
        PageFactory.initElements(driver, this);
        configurePages();
        initializeConstants();
    }

    public void configurePages() {
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        createdDashboardPage = new CreatedDashboardPage(driver);
    }

    public void quitDriver() {
        DriverProvider.getInstance().quit();
        DriverProvider.removeInstance();
    }

    public void initializeConstants() {
        login = propertiesHelper.getProperty("login");
        password = propertiesHelper.getProperty("password");
    }

    public void cleanUp() {
        String dashboardName = session.get(SessionKey.DASHBOARD_NAME, String.class);
        createdDashboardPage
                .goToDashboardPage()
                .deleteDashboard(dashboardName);
    }

    public void restoreInitialStateAfterEditing() {
        String name = session.get(SessionKey.DASHBOARD_NAME, String.class);
        String initialName = session.get(SessionKey.DASHBOARD_INITIAL_NAME, String.class);
        String description = session.get(SessionKey.DASHBOARD_DESCRIPTION, String.class);

        dashboardPage
                .clickDashboardEditIcon(name)
                .enterDashboardName(initialName)
                .enterDashboardDescription(description)
                .clickUpdateButton();
    }
}
