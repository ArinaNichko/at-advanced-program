package org.example.utils;

import org.example.exception.TestExecutionException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
    private static final Properties PROPERTIES = new Properties();
    public static PropertiesHelper propertiesHelper;

    private PropertiesHelper() {
        this.initializeProperties();
    }

    public static PropertiesHelper getInstance() {
        if (propertiesHelper == null) {
            propertiesHelper = new PropertiesHelper();
        }
        return propertiesHelper;
    }

    public String getProperty(String propertyName) {
        return PROPERTIES.getProperty(propertyName);
    }

    private void initializeProperties() {
        String path = "src/main/resources/config.properties";
        try {
            PROPERTIES.load(new FileReader(path));
        } catch (FileNotFoundException fileNotFoundException) {
            throw new TestExecutionException("There is NO file with properties in path: %s", path);
        } catch (IOException ioException) {
            throw new TestExecutionException("Problem with properties file: %s", ioException.getCause());
        }
    }
}
