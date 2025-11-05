package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manages configuration properties loaded from the environment.properties file.
 * Ensures the properties are loaded only once (Singleton pattern for efficiency).
 */
public class ConfigManager {

    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "config/environment.properties";

    // Static block to ensure properties are loaded when the class is initialized
    static {
        loadProperties();
    }

    private static void loadProperties() {
        logger.info("Loading configuration from: {}", CONFIG_FILE);

        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                logger.error("FATAL: Configuration file '{}' not found in resources.", CONFIG_FILE);
                throw new IOException("Configuration file not found: " + CONFIG_FILE);
            }
            properties.load(input);
            logger.info("Configuration successfully loaded.");
        } catch (IOException ex) {
            logger.fatal("Failed to load configuration properties: {}", ex.getMessage());
            // Exit immediately if critical configuration cannot be loaded
            System.exit(1);
        }
    }

    /**
     * Retrieves a string property value.
     * @param key The key of the property.
     * @return The string value.
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Configuration key '{}' not found. Returning null.", key);
        }
        return value;
    }

    /**
     * Retrieves an integer property value.
     * @param key The key of the property.
     * @return The integer value, or -1 if the key is missing or the value is not an integer.
     */
    public static int getIntProperty(String key) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                logger.error("Configuration key '{}' has a non-integer value: {}", key, value);
            }
        }
        return -1; // Indicate failure or missing value
    }
}
