package org.example.driver;


import org.example.driver.managers.ChromeDriverManager;
import org.example.driver.managers.EdgeDriverManager;
import org.example.driver.managers.WebDriverCreator;
import org.openqa.selenium.WebDriver;

public class DriverProvider {
  private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
  private static final ThreadLocal<String> BROWSER_THREAD = new ThreadLocal<>();
  private DriverProvider() {
    WebDriverCreator creator = createDriver(BROWSER_THREAD.get());
    driver.set(creator.createWebDriver());
  }

  public static WebDriver getInstance() {
    if (driver.get() == null) {
      driver.set(new DriverProvider().getDriver());
    }
    return driver.get();
  }

  public static void removeInstance() {
    driver.remove();
  }

  public WebDriver getDriver() {
    return driver.get();
  }

  public WebDriverCreator createDriver(String browser) {
    if (browser.equals("chrome")) {
      return new ChromeDriverManager();
    } else {
      return new EdgeDriverManager();
    }
  }

  public static void initialize(String browser) {
    BROWSER_THREAD.set(browser);
  }
}
