package org.example.pages;

import org.example.exception.TestExecutionException;
import org.example.setUp.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.example.utils.WaitUtils.getDriverWait;

public class DashboardPage extends BasePage {
    private static final String PENCIL_ICON_XPATH = "./parent::div//i[contains(@class, 'pencil')]";
    private static final String BORDER_COLOR = "border-color";
    private static final String EXPECTED_RED_COLOR = "rgb(255, 50, 34)";

    @FindBy(xpath = "//div[@data-id='164284']")
    private WebElement demoDashboard;
    @FindBy(xpath = "//div[contains(@class, 'addDashboardButton')]/button")
    private WebElement addNewDashboardButton;
    @FindBy(xpath = "//input[@placeholder='Enter dashboard name']")
    private WebElement dashboardNamePlaceholder;
    @FindBy(xpath = "//textarea[@placeholder='Enter dashboard description']")
    private WebElement dashboardDescriptionDashboard;
    @FindBy(xpath = "//button[contains(@class, 'bigButton__color-booger--EpRlL')]")
    private WebElement addButton;
    @FindBy(xpath = "//div[contains(@class, 'modal-window-animation-enter-done')]")
    private WebElement dashboardWindow;
    @FindBy(xpath = "//button[contains(@class, ' bigButton__color-gray-60--fySwY')]")
    private WebElement cancelButton;
    @FindBy(xpath = "//div[@class='gridRow__grid-row--X9wIq']/a")
    private List<WebElement> dashboards;
    @FindBy(xpath = "//button[text()='Update']")
    private WebElement updateButton;
    @FindBy(xpath = "//button[text()='Delete']")
    private WebElement deleteButton;
    @FindBy(xpath = "//input[@placeholder='Search by name']")
    private WebElement searchPlaceholder;
    @FindBy(xpath = "//div[contains(@class, 'noResultsForFilter')]")
    private WebElement noResultMessage;


    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public DashboardPage selectDemoDashboard() {
        getDriverWait().until(ExpectedConditions.elementToBeClickable(demoDashboard)).click();
        return this;
    }

    public DashboardPage clickAddNewDashboardButton() {
        waitAndClick(addNewDashboardButton);
        return this;
    }

    public DashboardPage enterDashboardName(String name) {
        getDriverWait().until(ExpectedConditions.visibilityOf(dashboardNamePlaceholder));
        dashboardNamePlaceholder.clear();
        dashboardNamePlaceholder.sendKeys(name);
        return this;
    }

    public DashboardPage enterDashboardDescription(String description) {
        dashboardDescriptionDashboard.clear();
        dashboardDescriptionDashboard.sendKeys(description);
        return this;
    }

    public void clickAddButton() {
        waitAndClick(addButton);
    }

    public boolean isDashboardNameHighlightedRed() {
        ExpectedCondition<Boolean> isPlaceholderRed = input ->
                dashboardNamePlaceholder.getCssValue(BORDER_COLOR).equals(EXPECTED_RED_COLOR);
        return getDriverWait().until(isPlaceholderRed);
    }

    public boolean isAddDashboardWindowOpened() {
        return dashboardWindow.isDisplayed();
    }

    public WebElement getDashboard(String name) {
        waitDashboardsHasExpectedSize();
        ExpectedCondition<Boolean> isNameDisplayed = input -> dashboards.stream()
                .anyMatch(dashboard -> dashboard.getText().equals(name));
        getDriverWait().until(isNameDisplayed);
        return dashboards.stream()
                .filter(dashboard -> dashboard.getText().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new TestExecutionException("Dashboard list is not have this item - {}", name));
    }

    public DashboardPage clickDashboardEditIcon(String name) {
        waitAndClick(getDashboard(name).findElement(By.xpath(PENCIL_ICON_XPATH)));
        return this;
    }

    public void clickUpdateButton() {
        waitAndClick(updateButton);
    }

    public boolean isDashboardDisplayed(String name) {
        return getDashboard(name).isDisplayed();
    }

    public void searchByName(String name) {
        getDriverWait().until(ExpectedConditions.visibilityOf(searchPlaceholder));
        searchPlaceholder.clear();
        searchPlaceholder.sendKeys(name);
    }

    public void waitDashboardsHasExpectedSize() {
        ExpectedCondition<Boolean> doesDashboardsHasExpectedSize = input -> dashboards.size() >= 2;
        getDriverWait().until(doesDashboardsHasExpectedSize);
    }

    public int getExpectedDashboardsSizeByName(String name) {
        waitDashboardsHasExpectedSize();

        return dashboards.stream()
                .filter(dashboard -> dashboard.getText().toLowerCase().contains(name.toLowerCase()))
                .toList()
                .size();
    }

    public int getActualDashboardsSize() {
        waitDashboardsHasExpectedSize();
        return dashboards.size();
    }

    public boolean verifyDashboardSize(int expectedSize) {
        ExpectedCondition<Boolean> isListUpdated = input -> dashboards.size() == expectedSize;
        return getDriverWait().until(isListUpdated);
    }

    public boolean isNoDashboardMessageDisplayed() {
        getDriverWait().until(ExpectedConditions.visibilityOf(noResultMessage));
        return noResultMessage.isDisplayed();
    }

    public void deleteDashboard(String name) {
        String deleteIconXpath = "./ancestor::div[1]//i[contains(@class, 'delete')]";
        WebElement dashboardDeleteIcon = getDashboard(name).findElement(By.xpath(deleteIconXpath));
        waitAndClick(dashboardDeleteIcon);
        waitAndClick(deleteButton);
    }
}
