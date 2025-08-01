package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import model.TestData;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.Helper;

public class ExtentTestNGListener implements ITestListener {

    private static ExtentReports extent;
    private static ExtentTest test;

    private ConfigFile configFile;

    public ExtentTestNGListener() {
        configFile = new ConfigFile();
    }

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(configFile.getReportPath());
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        Object[] parameters = result.getParameters();
        TestData params = (TestData) parameters[0];
        test = extent.createTest(methodName + " " + params.getTestCase());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = BrowserSetup.getLocalDriver();
        String methodName = result.getMethod().getMethodName();
        Object[] parameters = result.getParameters();
        TestData params = (TestData) parameters[0];
        String screenshotPath = Helper.takeScreenshot(driver, methodName+"_"+params.getTestCase());
        test.fail(result.getThrowable());
        test.addScreenCaptureFromPath(screenshotPath);
        test.fail("Test Failed: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip("Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
