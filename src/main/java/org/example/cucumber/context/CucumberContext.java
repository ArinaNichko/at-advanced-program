package org.example.cucumber.context;

import io.restassured.http.Method;
import org.example.clients.RestClient;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.example.utils.FileHelper.readJsonFileAsString;

public class CucumberContext {
    protected static PropertiesHelper propertiesHelper = PropertiesHelper.getInstance();
    public WebDriver driver;
    public static String login;
    public static String password;
    public static String initialDashBoardPath;
    public static String creationDashBoardPath;
    public static LoginPage loginPage;
    public static DashboardPage dashboardPage;
    public static CreatedDashboardPage createdDashboardPage;
    public static final Session session = new SessionImpl();
    public static RestClient restClient = new RestClient();

    public void setUp() {
        DriverProvider.initialize("chrome");
        driver = DriverProvider.getInstance();
        PageFactory.initElements(driver, this);
        configurePages();
        initializeConstants();
    }

    public void setUpApi() {
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
        initialDashBoardPath = propertiesHelper.getProperty("initialDashBoardPath");
        creationDashBoardPath = propertiesHelper.getProperty("creationDashBoardPath");
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

    public void cleanupApi() {
        String id = session.get(SessionKey.DASHBOARD_ID, String.class);
        restClient.sendRequestWithParams(Method.DELETE, id, Collections.emptyMap());
    }

    public void putInitialState() {
        String dashboard = readJsonFileAsString(initialDashBoardPath);
        String id = session.get(SessionKey.DASHBOARD_ID, String.class);
        restClient.sendRequestWithBody(Method.PUT, id, dashboard);
    }

    public void createDashboard() {
        String dashboard = readJsonFileAsString(creationDashBoardPath);
        restClient.sendRequestWithBody(Method.POST, "", dashboard);
        session.put(SessionKey.DASHBOARD_ID, String.class);
    }
}
