import java.io.FileInputStream;
import java.util.Properties;

public class ConfigPropertiesReader {

    private static final String CONFIG_FILE_PATH = "resources/config.properties";

    public static String getConfigPropertiesValue(String propertyKey) {
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties.getProperty(propertyKey);
        } catch (Exception e) {
            return null;
        }
    }
}
