package org.example.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.TestExecutionException;
import org.example.setUp.SelenideBasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Slf4j
public class SelenideDashboardPage extends SelenideBasePage {
    private static final String PENCIL_ICON_XPATH = "./parent::div//i[contains(@class, 'pencil')]";
    private static final String BORDER_COLOR = "border-color";
    private static final String EXPECTED_RED_COLOR = "rgb(255, 50, 34)";

    public SelenideDashboardPage() {
        super();
    }

    public void clickAddNewDashboardButton() {
        SelenideElement addNewDashboardButton =
                $(By.xpath("//div[contains(@class, 'addDashboardButton')]/button"));
        addNewDashboardButton.click();
        log.info("Add new dashboard button was clicked");
    }

    public SelenideDashboardPage enterDashboardName(String name) {
        SelenideElement dashboardNamePlaceholder =
                $(By.xpath("//input[@placeholder='Enter dashboard name']"));
        dashboardNamePlaceholder.clear();
        dashboardNamePlaceholder.sendKeys(name);
        log.info("Entering Dashboard name - {}", dashboardNamePlaceholder.getText());
        return this;
    }

    public SelenideDashboardPage enterDashboardDescription(String description) {
        SelenideElement dashboardDescriptionDashboard =
                $(By.xpath("//textarea[@placeholder='Enter dashboard description']"));
        dashboardDescriptionDashboard.clear();
        dashboardDescriptionDashboard.sendKeys(description);
        log.info("Entering Dashboard description - {}", dashboardDescriptionDashboard.getText());

        return this;
    }

    public void clickAddButton() {
        SelenideElement addButton =
                $(By.xpath("//button[contains(@class, 'bigButton__color-booger--EpRlL')]"));
        addButton.click();
        log.info("Add button was clicked");
    }

    public boolean isDashboardNameHighlightedRed() {
        SelenideElement dashboardNamePlaceholder =
                $(By.xpath("//input[@placeholder='Enter dashboard name']"));
        dashboardNamePlaceholder.shouldHave(cssValue(BORDER_COLOR, EXPECTED_RED_COLOR));
        boolean isNameHighlighted = dashboardNamePlaceholder.getCssValue(BORDER_COLOR)
                .equals(EXPECTED_RED_COLOR);
        if (isNameHighlighted) {
            log.info("Dashboard name is highlighted red");
            return true;
        } else {
            log.error("Dashboard name is NOT highlighted red");
            return false;
        }
    }

    public boolean isAddDashboardWindowOpened() {
        SelenideElement dashboardWindow =
                $(By.xpath("//div[contains(@class, 'modal-window-animation-enter-done')]"));
        if(dashboardWindow.isDisplayed()) {
            log.info("Add dashboard window is opened");
            return true;
        } else {
            log.error("Add dashboard window is NOT opened");
            return false;
        }
    }

    public SelenideElement getDashboard(String name) {
        ElementsCollection dashboards =
                $$(By.xpath("//div[@class='gridRow__grid-row--X9wIq']/a"));
        dashboards.shouldHave(sizeGreaterThan(2));

        return getSelenideElements(dashboards).stream()
                .filter(dashboard -> dashboard.getText().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new TestExecutionException("Dashboard list is not have this item - {}", name));
    }




    public SelenideDashboardPage clickDashboardEditIcon(String name) {
        SelenideElement pencilIcon = getDashboard(name).$(By.xpath(PENCIL_ICON_XPATH));
        pencilIcon.click();
        log.info("Dashboard {} pencil icon was clicked", name);
        return this;
    }

    public void clickUpdateButton() {
        SelenideElement updateButton = $(By.xpath("//button[text()='Update']"));
        updateButton.click();
        log.info("Update button was clicked");
    }

    public boolean isDashboardDisplayed(String name) {
        if(getDashboard(name).isDisplayed()) {
            log.info("Dashboard {} is displayed", name);
            return true;
        } else {
            log.error("Dashboard {} is NOT displayed", name);
            return false;
        }
    }


    public void deleteDashboard(String name) {
        String deleteIconXpath = "./ancestor::div[1]//i[contains(@class, 'delete')]";
        SelenideElement dashboardDeleteIcon = getDashboard(name).$(By.xpath(deleteIconXpath));
        SelenideElement deleteButton = $(By.xpath("//button[text()='Delete']"));
        dashboardDeleteIcon.click();
        deleteButton.click();
    }

    public void choseDashboard(String name) {
        getDashboard(name).click();
    }
}
