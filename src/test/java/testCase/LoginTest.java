package testCase;

import config.ExtentTestNGListener;
import model.LoginTestData;
import org.testng.annotations.*;
import page.LoginPageLocator;
import util.Helper;

import java.io.IOException;

import static config.BrowserSetup.initializationOfBrowser;
import static config.BrowserSetup.navigateToSite;
import static config.BrowserSetup.closeBrowser;


@Listeners(ExtentTestNGListener.class)
public class LoginTest {

    LoginPageLocator loginPage;

    @DataProvider(name = "loginData")
    public Object[][] loginTestData() throws IOException {
        return Helper.readLoginTestData();
    }

    @BeforeSuite
    public void browserInit() {
        initializationOfBrowser();
        loginPage = new LoginPageLocator();
    }

    @Test(dataProvider = "loginData")
    public void validate_login(LoginTestData data) {
        navigateToSite();
        loginPage.DoLogin(data.getUsername(), data.getPassword());
        loginPage.isloginSuccessful(data.getTestCaseType());
    }

    @AfterSuite()
    public void destory() {
        closeBrowser();
    }
}
