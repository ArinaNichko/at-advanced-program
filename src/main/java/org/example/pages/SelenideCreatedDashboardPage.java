package org.example.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.example.setUp.SelenideBasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class SelenideCreatedDashboardPage extends SelenideBasePage {

    public String getBreadcrumbTitle() {
        SelenideElement breadcrumbTitle =
                $(By.xpath("//ul[contains(@class,'pageBreadcrumbs')]/li[2]/child::span"));
        breadcrumbTitle.shouldBe(visible);
        log.info("Breadcrumb title is {}", breadcrumbTitle.getText());
        return breadcrumbTitle.getText().toLowerCase();
    }

    public SelenideDashboardPage goToDashboardPage() {
       SelenideElement dashboardIcon =
               $(By.xpath("//div[@class='sidebarButton__sidebar-nav-btn--gbV_N']/a[contains(@href, 'dashboard')]"));
       dashboardIcon.click();
       log.info("Dashboard icon was clicked");
        return new SelenideDashboardPage();
    }

    public void clickAddNewWidget() {
        $(By.xpath("//span[text()='Add new widget']")).should(visible).click();
        new SelenideWidgetPage();
    }
}
