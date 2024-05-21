package org.example.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.TestExecutionException;
import org.example.setUp.SelenideBasePage;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Slf4j
public class SelenideWidgetPage extends SelenideBasePage {
    private static final String BORDER_COLOR = "border-color";
    private static final String EXPECTED_RED_COLOR = "rgb(255, 50, 34)";
    public static final int FIRST = 0;

    public void selectWidgetType(String type) {
        ElementsCollection types = $$(By.xpath("//input[@name='widget-type']"));
        types.shouldHave(sizeGreaterThan(15));
        getListByValue(getSelenideElements(types), type).click();
        log.info("Widget {} type was clicked", type);
    }

    public void clickNextStep() {
        SelenideElement nextStep = $(By.xpath("//span[text()='Next step']"));
        nextStep.should(interactable);
        Selenide.executeJavaScript("arguments[0].click();", nextStep);
        log.info("Next step was clicked");
    }

    public void selectFilter() {
        ElementsCollection filters = $$(By.xpath("//input[@name='filterId']/parent::label"));
        filters.shouldHave(sizeGreaterThanOrEqual(1));
        filters.get(FIRST).click();
        log.info("First filter item was clicked");
    }

    public SelenideElement getListByValue(List<SelenideElement> elements, String value) {
        return elements.stream()
                .filter(type -> Objects.equals(type.getValue(), value))
                .findFirst()
                .map(type -> type.$(By.xpath("./parent::label")))
                .orElseThrow(() ->
                        new TestExecutionException("Element is not have this item - {}", value));
    }

    public void enterWidgetName(String name) {
        SelenideElement nameInput = $(By.cssSelector("input[placeholder='Enter widget name']"));
        nameInput.should(visible).clear();
        nameInput.sendKeys(name);
        log.info("Enter {} name in widget name input", name);
    }

    public void clickAddButton() {
        $(By.xpath("//button[text()='Add']")).click();
        log.info("Add button was clicked");
    }

    public SelenideElement getWidget(String title) {
        ElementsCollection widgets =
                $$(By.xpath("//div[contains(@class, 'widgetHeader__widget-name-block--AOAHS')]"));
        Selenide.refresh();
        widgets.shouldHave(anyMatch("Check if new widget exists in the list",
                widget -> widget.getText().equals(title)));

        return getSelenideElements(widgets).stream()
                .filter(widget -> widget.getText().equals(title))
                .findFirst()
                .orElseThrow(() ->
                        new TestExecutionException("Widget {} is not in the list", title));
    }

    public boolean verifyWidget(String title) {
        if (getWidget(title).isDisplayed()) {
            log.info("{} widget is displayed", title);
            return true;
        }
        log.error("{} widget is NOT displayed", title);
        return false;
    }

    public boolean isWidgetNameHighlightedRed() {
        SelenideElement widgetNamePlaceholder =
                $(By.xpath("//input[@placeholder='Enter widget name']"));
        widgetNamePlaceholder.shouldHave(cssValue(BORDER_COLOR, EXPECTED_RED_COLOR));
        boolean isNameHighlighted = widgetNamePlaceholder
                .getCssValue(BORDER_COLOR)
                .equals(EXPECTED_RED_COLOR);
        if (isNameHighlighted) {
            log.info("Widget name is highlighted red");
            return true;
        } else {
            log.error("Widget name is NOT highlighted red");
            return false;
        }
    }

    public void deleteWidget(String title) {
        SelenideElement deleteButton = $(By.xpath("//button[text()='Delete']"));
        clickWidgetDeleteIcon(title);
        deleteButton.click();
        log.info("Delete button was clicked");
    }

    public void clickWidgetDeleteIcon(String title) {
        String deleteIconXpath = "./../parent::div/following-sibling::div//div[contains(@class, 'SQilp')][3]";
        SelenideElement widgetDeleteIcon = getWidget(title).$(By.xpath(deleteIconXpath));
        widgetDeleteIcon.shouldBe(exist);
        Selenide.executeJavaScript("arguments[0].click();", widgetDeleteIcon);
        log.info("{} widget delete icon was clicked", title);
    }

    public boolean verifyWidgetPopup() {
        boolean isPopupDisplayed = $(By.xpath("//div[contains(@class, 'widgetWizardModal__modal-window--Kh7Ff')]"))
                .should(visible)
                .isDisplayed();
        if (isPopupDisplayed) {
            log.info("Widget popup is displayed");
            return true;
        }
        log.error("Widget popup is NOT displayed");
        return false;
    }
}