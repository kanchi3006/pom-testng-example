package page;

import config.BrowserSetup;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import util.FrameworkConstants;
import util.Helper;

import java.util.Objects;

public class LoginPageLocator extends BrowserSetup {

    private static final Logger logger = LogManager.getLogger(LoginPageLocator.class);
    Helper helper = new Helper();
    public LoginPageLocator() {
        PageFactory.initElements(BrowserSetup.getLocalDriver(), this);
    }
    @FindBy(id="submit")
    WebElement submitCTA;
    @FindBy(id="username")
    WebElement username;
    @FindBy(id="password")
    WebElement passWord;

    @FindBy(className="post-title")
    WebElement afterLoginText;
    public void DoLogin(String userName , String password){
        logger.info(String.format("Performing login for user %s", userName));
        helper.sendText(username,userName);
        helper.sendText(passWord,password);
        helper.clickElement(submitCTA);
    }
    public void isloginSuccessful(String type){
        if(Objects.nonNull(type) && FrameworkConstants.VALID.equalsIgnoreCase(type)) {
            Assert.assertTrue(helper.presenceOfElement(afterLoginText), FrameworkConstants.LOGIN_NOT_SUCCESSFUL);
        } else {
            Assert.assertFalse(helper.presenceOfElement(afterLoginText), FrameworkConstants.LOGIN_SUCCESSFUL);
        }

    }
}
