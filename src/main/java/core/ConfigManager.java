package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        // Crashing early here is intentional.
        // If the config is missing, we’d spend hours debugging random NullPointers later in the suite.
        try (FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            // No point in proceeding if the foundation layer is missing.
            throw new RuntimeException("Fatal: config.properties is missing or unreadable. Check src/test/resources/config/", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        try {
            // People often leave trailing spaces in property files by accident.
            // .trim() is a cheap insurance policy against NumberFormatExceptions.
            return (value != null) ? Integer.parseInt(value.trim()) : defaultValue;
        } catch (NumberFormatException e) {
            // Better to fall back to a safe default than to kill the whole test run over a typo.
            return defaultValue;
        }
    }

    public static int getIntProperty(String key) {
        return getIntProperty(key, 0);
    }
}