package com.qa.mobile.driver;

import com.qa.mobile.config.DeviceConfig;
import io.appium.java_client.AppiumDriver;

public final class DriverManager {

    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void createDriver(DeviceConfig deviceConfig) {
        AppiumDriver driver = DriverFactoryProvider.getFactory().createDriver(deviceConfig);

        DRIVER.set(driver);
    }

    public static AppiumDriver getDriver() {
        AppiumDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "Appium driver has not been initialized for the current thread");
        }

        return driver;
    }

    public static void quitDriver() {
        AppiumDriver driver = DRIVER.get();

        if (driver != null) {
            try {
                driver.quit();
            } finally {
                DRIVER.remove();
            }
        }
    }
}
