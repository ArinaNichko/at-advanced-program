package org.example.utils;

import org.example.driver.DriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ScreenshotsUtils {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotsUtils.class);

    public static void captureAndLogScreenshot( String description) {
        // Capture screenshot
        byte[] screenshot = captureScreenshot(DriverProvider.getInstance());

        // Log screenshot
        logScreenshot(screenshot, description);
    }

    private static byte[] captureScreenshot(WebDriver driver) {
        // Capture screenshot as byte array
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    private static void logScreenshot(byte[] screenshot, String description) {
        // Log screenshot using logback
        logger.info(description); // Log description
        logger.info("Screenshot: {}", screenshot);
        saveScreenshotToFile(screenshot, "reports/screenshots/1");// Log screenshot as byte array
    }

    public static void saveScreenshotToFile(byte[] screenshot, String fileName) {
        // Save screenshot to file
        try {
            Files.write(new File(fileName).toPath(), screenshot);
        } catch (IOException e) {
            logger.error("Failed to save screenshot to file", e);
        }
    }
}
