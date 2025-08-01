package config;

import exception.BrowserNotSupportedException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import util.FrameworkConstants;

import java.time.Duration;

public class BrowserSetup {

    private static final Logger logger = LogManager.getLogger(BrowserSetup.class);
    private static WebDriver driver;

    private static final ConfigFile configFile = new ConfigFile();

    public static void initializationOfBrowser() {
        logger.info("-----Browser Initialization start-----");
        String browserName = configFile.getBrowserName();
        switch (browserName.toLowerCase()) {
            case FrameworkConstants.CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case FrameworkConstants.EDGE:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new BrowserNotSupportedException(String.format(FrameworkConstants.BROWSER_NOT_SUPPORTED, browserName));
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(configFile.getWaitDuration()));
        logger.info("-----Browser Initialization end-----");
    }
    public static WebDriver getLocalDriver() {
        return driver;
    }
    public static void navigateToSite() {
        driver.get(configFile.getTestUrl());
    }

    public static void closeBrowser() {
        getLocalDriver().quit();
    }
}
