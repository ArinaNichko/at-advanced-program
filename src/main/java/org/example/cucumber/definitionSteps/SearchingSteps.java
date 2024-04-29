package org.example.cucumber.definitionSteps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.cucumber.context.CucumberContext;
import org.example.session.SessionKey;
import org.testng.Assert;

import static org.example.cucumber.context.CucumberContext.dashboardPage;
import static org.example.cucumber.context.CucumberContext.session;

public class SearchingSteps  {

    private final CucumberContext context;

    public SearchingSteps(CucumberContext context) {
        this.context = context;
    }

    @Before
    public void setup() {
        context.setUp();
    }

    @After(order = 1)
    public void tearDown() {
        context.quitDriver();
    }

    @When("user searches by {string}")
    public void searchByName(String partialName) {
        session.put(SessionKey.EXPECTED_DASHBOARD_SIZE
                ,  dashboardPage.getExpectedDashboardsSizeByName(partialName));
        dashboardPage.searchByName(partialName);
    }

    @But("user sees zero result")
    public void verifyZeroResult() {
        Assert.assertTrue(dashboardPage.isNoDashboardMessageDisplayed());

    }

    @Then("dashboard list size equals expected result")
    public void verifyDashboardListSize() {
        Assert.assertTrue(dashboardPage.verifyDashboardSize(
                session.get(SessionKey.EXPECTED_DASHBOARD_SIZE, Integer.class)
        ));
    }
}
