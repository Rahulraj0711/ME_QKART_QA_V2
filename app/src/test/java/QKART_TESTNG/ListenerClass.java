package QKART_TESTNG;

import java.io.File;
import java.time.LocalDateTime;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass extends QKART_Tests implements ITestListener {

    public static void takeScreenshot(RemoteWebDriver driver, String screenshotType, String description) {
        File scrDir = new File("/screenshots");
        if (!scrDir.exists()) {
            scrDir.mkdirs();
        }

        String timestamp = String.valueOf(LocalDateTime.now()).replace(":", "_");
        String path = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType, description);

        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("screenshots/" + path);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTestStart(ITestResult result) {
        takeScreenshot(driver, "TestStart", result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        takeScreenshot(driver, "TestSuccess", result.getName());
    }

    public void onTestFailure(ITestResult result) {
        takeScreenshot(driver, "TestFailure", result.getName());
    }
}