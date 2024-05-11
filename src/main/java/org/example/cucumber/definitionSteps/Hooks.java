package org.example.cucumber.definitionSteps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.example.cucumber.context.CucumberContext;

public class Hooks {
    private final CucumberContext context;

    public Hooks(CucumberContext context) {
        this.context = context;
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

    @After(value = "@uiSetup", order = 1)
    public void tearDown() {
        context.quitDriver();
    }

    @After(value = "@cleanup", order = 2)
    public void cleanUpUi() {
        context.cleanUp();
    }

    @After(value = "@restoreInitialState", order = 2)
    public void restoreInitialState() {
        context.restoreInitialStateAfterEditing();
    }
}
