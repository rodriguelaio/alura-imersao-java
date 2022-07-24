package br.com.guelaio.alurastickers.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigPropertiesReader {

    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    public static String getConfigPropertiesValue(String propertyKey) {
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties.getProperty(propertyKey);
        } catch (Exception e) {
            System.out.println("ConfigPropertiesReader Exception: ".concat(e.getMessage()));
            return null;
        }
    }
}
