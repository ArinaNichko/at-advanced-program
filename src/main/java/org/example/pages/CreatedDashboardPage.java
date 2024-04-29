package org.example.pages;

import org.example.setUp.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.example.utils.WaitUtils.getDriverWait;

public class CreatedDashboardPage extends BasePage {

    @FindBy(xpath = "//ul[contains(@class,'pageBreadcrumbs')]/li[2]/child::span")
    private WebElement breadcrumbTitle;
    @FindBy(xpath = "//button//child::span[text()='Delete']")
    private WebElement deleteButton;
    @FindBy(xpath = "//button[text()='Delete']")
    private WebElement popupDeleteButton;
    @FindBy(xpath = "//div[@class='sidebarButton__sidebar-nav-btn--gbV_N']/a[contains(@href, 'dashboard')]")
    private WebElement dashboardIcon;


    public CreatedDashboardPage(WebDriver driver) {
        super(driver);
    }

  public String getBreadcrumbTitle() {
        return getDriverWait().until(ExpectedConditions.visibilityOf(breadcrumbTitle)).getText().toLowerCase();
  }

  public DashboardPage goToDashboardPage() {
        waitAndClick(dashboardIcon);
        return new DashboardPage(driver);
  }
}
