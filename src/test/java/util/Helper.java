package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.LoginTestData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Helper {

    private static final Logger logger = LogManager.getLogger(Helper.class);
    public void clickElement(WebElement locator) {
        locator.click();
    }

    public void sendText(WebElement locator, String text) {
        locator.sendKeys(text);
    }

    public boolean presenceOfElement(WebElement locator) {
        try{
            return locator.isDisplayed();
        } catch (Exception ex) {
           logger.error(ex.getMessage());
        }
        return false;
    }

    public static Object[][] readLoginTestData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/test/java/testdata/LoginTestData.json");
        JsonNode root = mapper.readTree(file).get("loginTests");
        List<LoginTestData> testDataList = new ArrayList<>();
        Iterator<JsonNode> elements = root.elements();
        while (elements.hasNext()) {
            LoginTestData data = mapper.treeToValue(elements.next(), LoginTestData.class);
            testDataList.add(data);

        }

        Object[][] data = new Object[testDataList.size()][1];
        for (int i = 0; i < testDataList.size(); i++) {
            data[i][0] = testDataList.get(i);
        }
        return data;
    }

    public static String takeScreenshot(WebDriver driver, String testName) {
        String screenshotPath = "report/screenshots/" + testName + "_" + UUID.randomUUID() + ".png";
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File destFile = new File(screenshotPath);
            destFile.getParentFile().mkdirs();
            Files.copy(srcFile.toPath(), destFile.toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        screenshotPath=screenshotPath.replace("report/", "");
        return screenshotPath.replace("\\", "/");
    }
}
