package org.example.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class ExtentReporter implements ITestListener {

    public static final ThreadLocal<ExtentTest> TEST_REPORT = new ThreadLocal<>();
    static String timeStamp = new SimpleDateFormat("MM-dd-yyyy_HH_mm_ss")
            .format(Calendar.getInstance().getTime());
    public static ExtentReports extent;

    static {
        ExtentSparkReporter spark = new ExtentSparkReporter("./reports/Report " + timeStamp + ".html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("AT Advanced Program");
        spark.config().setReportName("Report portal");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testCaseName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        String testCategory = Arrays.toString(result.getMethod().getGroups());
        ExtentTest test = extent.createTest(testCaseName, testDescription)
                .assignCategory(testCategory);
        TEST_REPORT.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String logText = "TEST CASE: " + methodName.toUpperCase() + " PASSED";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        TEST_REPORT.get().pass(m);
    }

    public void onTestFailure(ITestResult result) {
        String failureLogg = "TEST CASE FAILED";
        Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
        TEST_REPORT.get().log(Status.FAIL, m);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String logText = "Test Case: " + methodName + " Skipped";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
        TEST_REPORT.get().skip(m);

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
