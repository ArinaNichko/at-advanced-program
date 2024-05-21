package org.example.cucumber.context;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.google.gson.JsonObject;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.http.entity.StringEntity;
import org.example.clients.HttpClient;
import org.example.clients.RestClient;
import org.example.driver.DriverProvider;
import org.example.exception.TestExecutionException;
import org.example.pages.*;
import org.example.session.Session;
import org.example.session.SessionImpl;
import org.example.session.SessionKey;
import org.example.utils.PropertiesHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

import static org.example.session.SessionKey.JSON_STRING;
import static org.example.utils.JsonHelper.*;

public class CucumberContext {
    protected static PropertiesHelper propertiesHelper = PropertiesHelper.getInstance();
    public WebDriver driver;
    public static String login;
    public static String password;
    public static String initialDashBoardPath;
    public static String creationDashBoardPath;
    public static LoginPage loginPage;
    public static SelenideLoginPage selenideLoginPage;
    public static DashboardPage dashboardPage;
    public static SelenideDashboardPage selenideDashboardPage;
    public static CreatedDashboardPage createdDashboardPage;
    public static SelenideCreatedDashboardPage selenideCreatedDashboardPage;
    public static SelenideWidgetPage selenideWidgetPage;
    public static final Session session = new SessionImpl();
    public static RestClient restClient = new RestClient();
    public static HttpClient httpClient = new HttpClient();

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
        selenideDashboardPage = new SelenideDashboardPage();
        selenideLoginPage = new SelenideLoginPage();
        selenideCreatedDashboardPage = new SelenideCreatedDashboardPage();
        selenideWidgetPage = new SelenideWidgetPage();

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

    public void cleanUpSelenide() {
        String dashboardName = session.get(SessionKey.DASHBOARD_NAME, String.class);
        selenideCreatedDashboardPage
                .goToDashboardPage()
                .deleteDashboard(dashboardName);
    }

    public void cleanupWidget() {
        Selenide.refresh();
        String dashboardName = session.get(SessionKey.WIDGET_NAME, String.class);
        selenideWidgetPage
                .deleteWidget(dashboardName);
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

    public void restoreInitialStateAfterEditingSelenide() {
        String name = session.get(SessionKey.DASHBOARD_NAME, String.class);
        String initialName = session.get(SessionKey.DASHBOARD_INITIAL_NAME, String.class);
        String description = session.get(SessionKey.DASHBOARD_DESCRIPTION, String.class);

        selenideDashboardPage
                .clickDashboardEditIcon(name)
                .enterDashboardName(initialName)
                .enterDashboardDescription(description)
                .clickUpdateButton();
    }

    public void cleanupApi() {
        String id = session.get(SessionKey.DASHBOARD_ID, String.class);
        restClient.sendRequestWithParams(Method.DELETE, id, Collections.emptyMap());
    }

    public void cleanupApacheApi() {
        String id = session.get(SessionKey.DASHBOARD_ID, String.class);
        httpClient.sendRequest("DELETE", id, null);
    }

    public void putInitialState() {
        String dashboard = readJsonFileAsString(initialDashBoardPath);
        String id = session.get(SessionKey.DASHBOARD_ID, String.class);
        restClient.sendRequestWithBody(Method.PUT, id, dashboard);
    }

    public void putInitialStateApache() {
        String dashboard = readJsonFileAsString(initialDashBoardPath);
        String id = session.get(SessionKey.DASHBOARD_ID, String.class);
        try {
            httpClient.sendRequest("PUT", id, new StringEntity(dashboard));
        } catch (UnsupportedEncodingException e) {
            throw new TestExecutionException("Can not encode string as StringEntity");
        }
    }

    public void createDashboard() {
        String dashboard = readJsonFileAsString(creationDashBoardPath);
        Response response = restClient.sendRequestWithBody(Method.POST, "", dashboard);
        String id = response.jsonPath().getString("id");
        session.put(SessionKey.DASHBOARD_ID, id);
    }

    public void createDashboardApache() {
        String dashboard = readJsonFileAsString(creationDashBoardPath);
        try {
            httpClient.sendRequest("POST", "", new StringEntity(dashboard));
        } catch (UnsupportedEncodingException e) {
            throw new TestExecutionException("Can not encode string as StringEntity");
        }
        String jsonString = session.get(JSON_STRING, String.class);
        JsonObject jsonObject = readStringAsJsonElement(jsonString);
        session.put(SessionKey.DASHBOARD_ID, getId(jsonObject));
    }

    public void runRemote() {
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browser = "firefox";
    }
}
