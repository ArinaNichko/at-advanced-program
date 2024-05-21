package org.example.cucumber.definitionSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.example.session.SessionKey;
import org.testng.Assert;

import static org.example.cucumber.context.CucumberContext.*;

@Slf4j
public class DashboardsSelenideSteps {

    @When("user log in ReportPortal selenide")
    public void logInViaSelenide() {
        selenideLoginPage
                .goToLoginPage()
                .enterLogin(login)
                .enterPassword(password)
                .clickLogin();
    }

    @And("user enters dashboard name -  {string} via selenide")
    public void enterNameViaSelenide(String name) {
        session.put(SessionKey.DASHBOARD_NAME, name);
        selenideDashboardPage.enterDashboardName(name);
    }

    @And("user enters dashboard description - {string} via selenide")
    public void enterDescriptionViaSelenide(String description) {
        selenideDashboardPage.enterDashboardDescription(description);
        session.put(SessionKey.DASHBOARD_DESCRIPTION, description);
    }

    @When("user clicks add new dashboard button via selenide")
    public void clickAddNewDashboardButtonViaSelenide() {
        selenideDashboardPage.clickAddNewDashboardButton();

    }

    @And("user clicks add button via selenide")
    public void clickAddButtonViaSelenide() {
        selenideDashboardPage.clickAddButton();
    }

    @Then("new dashboard page is created with {string} via selenide")
    public void verifyDashboardPageIsCreatedWithNameViaSelenide(String name) {
        Assert.assertEquals(name, selenideCreatedDashboardPage.getBreadcrumbTitle());
    }

    @Then("dashboard name is highlighted red via selenide")
    public void verifyDashboardNameIsHighlightedRedViaSelenide() {
        Assert.assertTrue(selenideDashboardPage.isDashboardNameHighlightedRed());
    }


    @Then("dashboard window is opened via selenide")
    public void verifyDashboardWindowIsOpenedViaSelenide() {
        Assert.assertTrue(selenideDashboardPage.isAddDashboardWindowOpened());
    }

    @When("user clicks dashboard edit icon for {string} via selenide")
    public void clickDashboardEditIconForNameViaSelenide(String initialName) {
        selenideDashboardPage.clickDashboardEditIcon(initialName);
        session.put(SessionKey.DASHBOARD_INITIAL_NAME, initialName);
    }


    @And("user clicks update button via selenide")
    public void clickUpdateButtonViaSelenide() {
        selenideDashboardPage.clickUpdateButton();
    }

    @Then("user sees new {string} dashboard in a list via selenide")
    public void verifyNewDashboardInListViaSelenide(String newName) {
        Assert.assertTrue(selenideDashboardPage.isDashboardDisplayed(newName));
        session.put(SessionKey.DASHBOARD_NAME, newName);
    }
}
