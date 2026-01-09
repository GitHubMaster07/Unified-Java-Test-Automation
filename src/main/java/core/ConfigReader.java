package core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility for safely loading the configuration file 
 */
public final class ConfigReader {
    // Logger for logging events and errors
    private static final Logger LOGGER = Logger.getLogger(ConfigReader.class.getName());

    // This file that contains ui.base.url
    private static final String PROPERTIES_FILE = "config/environment.properties";

    // Properties object to store loaded data
    private static final Properties PROPERTIES = new Properties();

    // Static initialization block for single-time file loading when the class is loaded
    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                // Critical error if the file is not found
                LOGGER.log(Level.SEVERE, "CRITICAL: Configuration file not found at " + PROPERTIES_FILE + ". Check location and spelling.");
            } else {
                try {
                    PROPERTIES.load(input);
                    LOGGER.log(Level.INFO, "Configuration file " + PROPERTIES_FILE + " successfully loaded.");
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Error reading configuration file " + PROPERTIES_FILE, ex);
                }
            }
        } catch (Exception ex) {
            // Catch any exception during stream handling
            LOGGER.log(Level.SEVERE, "Unexpected error during initialization of ConfigReader.", ex);
        }
    }

    /**
     * Private constructor to prevent utility class instantiation.
     */
    private ConfigReader() {
    }

    /**
     * Returns the property value by its key.
     *
     * @param key Key of the property to retrieve.
     * @return The property value as a String, or null if the property is not found.
     */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * Returns the property value by its key, parsed as an Integer.
     *
     * @param key Key of the property to retrieve.
     * @return The property value as an Integer, or 0 if the property is not found or not a valid integer.
     */
    public static int getIntProperty(String key) {
        String value = getProperty(key);
        if (value == null) {
            LOGGER.log(Level.WARNING, "Property '" + key + "' not found in configuration. Returning 0.");
            return 0;
        }
        try {
            // Trim whitespace before parsing
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Property '" + key + "' value '" + value + "' is not a valid integer. Returning 0.", e);
            return 0;
        }
    }
}
