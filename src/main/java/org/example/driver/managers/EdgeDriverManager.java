package org.example.driver.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public class EdgeDriverManager extends WebDriverCreator {
  @Override
  public WebDriver createWebDriver() {
    WebDriverManager.edgedriver().setup();

    EdgeOptions edgeOptions = new EdgeOptions();
    edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

    WebDriver driver = new EdgeDriver(edgeOptions);

    driver.manage().deleteAllCookies();
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    driver.manage().window().maximize();

    return driver;
  }
}
