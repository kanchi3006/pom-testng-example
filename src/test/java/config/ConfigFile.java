package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFile {
    private Properties properties;
    private static final Logger logger = LogManager.getLogger(ConfigFile.class);
    private final String propertyFilePath = "config//configuration.properties";

    public ConfigFile() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Unable to find property file in location " + propertyFilePath);
        }
    }

    public String getBrowserName() {
        String browser = properties.getProperty("browser-name");
        if (browser != null) return browser;
        else throw new RuntimeException("unable to find browser name in properties file");
    }

    public String getTestUrl() {
        String driverPath = properties.getProperty("test-url");
        if (driverPath != null) return driverPath;
        else throw new RuntimeException("unable to find test url in properties file");
    }

    public long getWaitDuration() {
        String waitDuration = properties.getProperty("waitDuration");
        if (waitDuration != null) return Long.parseLong(waitDuration);
        else throw new RuntimeException("unable to find wait duration in properties file");
    }
}