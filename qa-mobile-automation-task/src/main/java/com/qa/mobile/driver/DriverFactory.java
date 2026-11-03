package com.qa.mobile.driver;

import com.qa.mobile.config.DeviceConfig;
import io.appium.java_client.AppiumDriver;

public interface DriverFactory {

    AppiumDriver createDriver(DeviceConfig deviceConfig);
}