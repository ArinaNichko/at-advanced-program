package org.example.reporting;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.example.driver.DriverProvider;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TestListener implements ITestListener {


  public void onTestStart(ITestResult result) {

  }


  public void onTestSuccess(ITestResult result) {
    log.info("Passed test - " + result.getName());
  }

  public void onTestFailure(ITestResult result) {

    log.debug("Screenshot was saved: {}", getCurrentTimeAsString() + ".png");
  }

  public void onTestSkipped(ITestResult result) {
  }

  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
  }

  public void onTestFailedWithTimeout(ITestResult result) {
    saveScreenshot(result);
    log.debug("Screenshot was saved: {}", getCurrentTimeAsString() + ".png");
    this.onTestFailure(result);
  }

  public void onStart(ITestContext context) {
  }

  public void onFinish(ITestContext context) {
  }

  private void saveScreenshot(ITestResult result) {
    File screenCapture = ((TakesScreenshot) DriverProvider.getInstance())
            .getScreenshotAs(OutputType.FILE);
    try {
      FileUtils.copyFile(screenCapture, new File(
              ".//reports/screenshots/" + result.getName() + ".png"
      ));
    } catch (IOException e) {
      log.error("Failed to save screenshot: " + e.getLocalizedMessage());
    }
  }

  private String getCurrentTimeAsString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss");
    return ZonedDateTime.now().format(formatter);
  }
}
