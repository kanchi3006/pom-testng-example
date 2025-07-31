package testCase;

import model.LoginTestData;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page.LoginPageLocator;
import util.Helper;

import java.io.IOException;

import static config.BrowserSetup.initializationOfBrowser;
import static config.BrowserSetup.navigateToSite;

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

    @Test(dataProvider = "loginData" )
    public void loginTest(LoginTestData data){
        navigateToSite();
        loginPage.DoLogin(data.getUsername(), data.getPassword());
        loginPage.isloginSuccessful(data.getTestCaseType());
    }





}
