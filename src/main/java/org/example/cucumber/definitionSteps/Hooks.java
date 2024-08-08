package org.example.cucumber.definitionSteps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.cucumber.context.CucumberContext;
import org.example.session.SessionKey;

import static org.example.cucumber.context.CucumberContext.session;

public class Hooks {
    private final CucumberContext context;

    public Hooks(CucumberContext context) {
        this.context = context;
    }

    @Before
    public void before(Scenario scenario) {
        session.put(SessionKey.SCENARIO_NAME, scenario.getName());
        String name = session.get(SessionKey.SCENARIO_NAME, String.class);
        System.out.println(name);

    }

    @Before(value = "@selenideUiSetup")
    public void setupSelenideUi() {
//        context.runRemote();
//        context.setUp();
    context.configurePages();
        context.initializeConstants();
    }

    @Before(value = "@apiSetup")
    public void setUpApi() {
        context.initializeConstants();
    }

    @After(value = "@apiCleanup")
    public void cleanUp() {
        context.cleanupApi();
    }

    @After(value = "@apacheApiCleanup")
    public void cleanUpApache() {
        context.cleanupApacheApi();
    }

    @After(value = "@apiInitialState")
    public void putInitialState() {
        context.putInitialState();
    }

    @After(value = "@apacheApiInitialState")
    public void putInitialStateApache() {
        context.putInitialStateApache();
    }

    @Before(value = "@createDashboard")
    public void createDashboard() {
        context.createDashboard();
    }

    @Before(value = "@createDashboardApache")
    public void createDashboardApache() {
        context.createDashboardApache();
    }

    @Before(value = "@uiSetup")
    public void setup() {
        context.setUp();
    }

    @After(value = "@cleanupWidgetSelenide")
    public void cleanupWidgetSelenide() {
        context.cleanupWidget();
    }

    @After(value = "@uiSetup", order = 1)
    public void tearDown() {
        context.quitDriver();
    }


    @After(value = "@selenideUiSetup", order = 1)
    public void tearDownSelenide() {
        context.quitDriver();
    }

    @After(value = "@cleanup", order = 2)
    public void cleanUpUi() {
        context.cleanUp();
    }

    @After(value = "@cleanupSelenide", order = 2)
    public void cleanUpUiSelenide() {
        context.cleanUpSelenide();
    }


    @After(value = "@restoreInitialState", order = 2)
    public void restoreInitialState() {
        context.restoreInitialStateAfterEditing();
    }

    @After(value = "@restoreInitialStateSelenide", order = 2)
    public void restoreInitialStateSelenide() {
        context.restoreInitialStateAfterEditingSelenide();
    }
}
