package org.example.cucumber.definitionSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.example.session.SessionKey;
import org.example.utils.ScreenshotsUtils;
import org.testng.Assert;

import static org.example.cucumber.context.CucumberContext.*;

@Slf4j
public class CreationAndEditionSteps {

    @When("^user clicks add new dashboard button$")
    public void clickAddNewDashboardButton() {
        dashboardPage.clickAddNewDashboardButton();
    }

    @And("user clicks add button")
    public void clickAddButton() {
        dashboardPage.clickAddButton();
    }

    @And("user enters dashboard name -  {string}")
    public void enterDashboardName(String name) {
        session.put(SessionKey.DASHBOARD_NAME, name);
        dashboardPage.enterDashboardName(name);
    }

    @And("user enters dashboard description - {string}")
    public void enterDashboardDescription(String description) {
        dashboardPage.enterDashboardDescription(description);
        session.put(SessionKey.DASHBOARD_DESCRIPTION, description);
    }

    @Then("dashboard list needs to have new dashboard with {string}")
    public void verifyDashboardListContainNew(String name) {
        Assert.assertEquals(name, createdDashboardPage.getBreadcrumbTitle());
    }

    @When("user log in ReportPortal")
    public void logInReportPortal() {
        loginPage
                .goToLoginPage()
                .enterLogin(login)
                .enterPassword(password)
                .clickLogin();
    }

    @Then("dashboard window is opened")
    public void verifyDashboardWindowIsOpened() {
        Assert.assertTrue(dashboardPage.isAddDashboardWindowOpened());

    }

    @Then("dashboard name is highlighted red")
    public void verifyDashboardNameIsHighlightedRed() {
        Assert.assertTrue(dashboardPage.isDashboardNameHighlightedRed());
    }

    @Then("new dashboard page is created with {string}")
    public void verifyNewDashboardPageIsCreatedWithName(String name) {
        Assert.assertEquals(name, createdDashboardPage.getBreadcrumbTitle());

    }

    @When("user clicks dashboard edit icon for {string}")
    public void clickDashboardEditIconForName(String initialName) {
        dashboardPage.clickDashboardEditIcon(initialName);
        session.put(SessionKey.DASHBOARD_INITIAL_NAME, initialName);
    }

    @And("user clicks update button")
    public void clickUpdateButton() {
        dashboardPage.clickUpdateButton();

    }

    @Then("user sees new {string} dashboard in a list")
    public void verifyNewDashboardInList(String newName) {
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(newName));
        session.put(SessionKey.DASHBOARD_NAME, newName);
    }

    @When("Capture screenshot")
    public void captureScreenshot() {
        ScreenshotsUtils.captureAndLogScreenshot("Example step");
    }
}
