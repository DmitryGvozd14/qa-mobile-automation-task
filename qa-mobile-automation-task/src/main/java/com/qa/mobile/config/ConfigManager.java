
package com.qa.mobile.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final String CONFIG_FILE = "config/config.properties";
    private static final Properties PROPERTIES = new Properties();
    private static final String PLATFORM_PROPERTY = "platform";
    private static final String PLATFORM_ENV_VARIABLE = "PLATFORM";
    private static final String DEFAULT_PLATFORM = "ANDROID";
    private static final String APP_PACKAGE_PROPERTY = "app.package";

    static {
        try (InputStream input = ConfigManager.class
            .getClassLoader()
            .getResourceAsStream(CONFIG_FILE)) {

        if (input == null) {
            throw new IllegalStateException("Configuration file not found: " + CONFIG_FILE);
        }

        PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    private ConfigManager() {
    }

    public static Platform getPlatform() {
        String platform = System.getProperty(PLATFORM_PROPERTY);

        if (platform == null || platform.isBlank()) {
            platform = System.getenv(PLATFORM_ENV_VARIABLE);
        }

        if (platform == null || platform.isBlank()) {
            platform = PROPERTIES.getProperty(PLATFORM_PROPERTY, DEFAULT_PLATFORM);
        }

        try {
            return Platform.valueOf(platform.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported platform: " + platform, e);
        }
    }

    public static String getProperty(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing or empty configuration property: " + key);
        }

        return value.trim();
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public static String getAppPackage() {
        return getProperty(APP_PACKAGE_PROPERTY);
    }
}
