package org.example.setUp;

import lombok.extern.slf4j.Slf4j;
import org.example.utils.PropertiesHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.util.Optional.ofNullable;
import static org.example.utils.WaitUtils.getDriverWait;

@Slf4j
public class BasePage {

    protected JavascriptExecutor javascriptExecutor;
    protected WebDriver driver;
    public static PropertiesHelper propertiesHelper;
    protected static final Duration timeout = Duration.ofDays(15);

    protected BasePage(WebDriver driver) {
        propertiesHelper = PropertiesHelper.getInstance();
        this.driver = driver;
        javascriptExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }


    public void waitAndClick(WebElement  element) {
        getDriverWait().until(ExpectedConditions.elementToBeClickable(element)).click();
        log.info("{} is clicked", ofNullable(element.toString()).orElse(element.getText()));
    }
}
