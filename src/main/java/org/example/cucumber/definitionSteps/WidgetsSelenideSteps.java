package org.example.cucumber.definitionSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import static org.example.cucumber.context.CucumberContext.*;
import static org.example.session.SessionKey.WIDGET_NAME;

@Slf4j
public class WidgetsSelenideSteps {

    @When("user chooses {string} dashboard via selenide")
    public void choseDashboard(String dashboardName) {
        selenideDashboardPage.choseDashboard(dashboardName);
    }

    @And("user clicks add new widget button")
    public void clickAddNewWidgetButton() {
        selenideCreatedDashboardPage.clickAddNewWidget();
    }

    @And("user chooses widget {string} from list")
    public void chooseWidgetType(String type) {
        selenideWidgetPage.selectWidgetType(type);
    }

    @And("user clicks next step")
    public void clickNextStep() {
        selenideWidgetPage.clickNextStep();
    }

    @And("user enters widget name -  {string}")
    public void userEntersWidgetName(String name) {
        selenideWidgetPage.enterWidgetName(name);
        session.put(WIDGET_NAME, name);
    }

    @Then("new widget is created with {string} in dashboard")
    public void verifyNewWidgetIsCreated(String name) {
        Assert.assertTrue(selenideWidgetPage.verifyWidget(name));
    }

    @And("user clicks add widget button")
    public void clickAddWidgetButton() {
        selenideWidgetPage.clickAddButton();
    }

    @Then("widget name is highlighted red via selenide")
    public void verifyWidgetNameIsHighlightedRed() {
        Assert.assertTrue(selenideWidgetPage.isWidgetNameHighlightedRed());
    }

    @Then("widget popup is not closed")
    public void verifyWidgetPopupIsNotClosed() {
        Assert.assertTrue(selenideWidgetPage.verifyWidgetPopup());
    }

    @And("user chooses filter from the list below")
    public void choosesFilterById() {
        selenideWidgetPage.selectFilter();
    }
}
