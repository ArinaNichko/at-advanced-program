package org.example.utils;

import org.example.driver.DriverProvider;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class WaitUtils {

    public static WebDriverWait getDriverWait() {
        return new WebDriverWait(DriverProvider.getInstance(), Duration.ofSeconds(10));
    }

    public static FluentWait<WebDriver> getDriverFluentWait(long timeout, long interval) {
        return new WebDriverWait(DriverProvider.getInstance(), Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(interval))
                .withMessage("Timeout occurred!")
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }
}