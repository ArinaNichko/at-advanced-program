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


    public CreatedDashboardPage(WebDriver driver) {
        super(driver);
    }

  public String getBreadcrumbTitle() {
        return getDriverWait().until(ExpectedConditions.visibilityOf(breadcrumbTitle)).getText().toLowerCase();
  }
}
