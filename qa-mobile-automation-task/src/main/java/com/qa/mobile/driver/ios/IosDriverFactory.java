package com.qa.mobile.driver.ios;

import com.qa.mobile.config.ConfigManager;
import com.qa.mobile.config.DeviceConfig;
import com.qa.mobile.driver.DriverFactory;
import io.appium.java_client.ios.IOSDriver;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IosDriverFactory implements DriverFactory {

    private static final String PLATFORM_NAME = "iOS";
    private static final String AUTOMATION_NAME = "XCUITest";
    private static final String CAPABILITY_PLATFORM_NAME = "platformName";
    private static final String CAPABILITY_PLATFORM_VERSION = "platformVersion";
    private static final String CAPABILITY_UDID = "udid";
    private static final String CAPABILITY_AUTOMATION_NAME = "automationName";
    private static final String CAPABILITY_BUNDLE_ID = "bundleId";
    private static final String APPIUM_SERVER_URL_PROPERTY = "appium.server.url";
    private static final String APP_BUNDLE_ID_PROPERTY = "app.bundle.id";

    @Override
    public IOSDriver createDriver(DeviceConfig deviceConfig) {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(CAPABILITY_PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(CAPABILITY_PLATFORM_VERSION, deviceConfig.platformVersion());
        capabilities.setCapability(CAPABILITY_UDID, deviceConfig.udid());
        capabilities.setCapability(CAPABILITY_AUTOMATION_NAME, AUTOMATION_NAME);
        capabilities.setCapability(CAPABILITY_BUNDLE_ID, ConfigManager.getProperty(APP_BUNDLE_ID_PROPERTY));

        return new IOSDriver(getServerUrl(), capabilities);
    }

    private URL getServerUrl() {
        try {
            return URI.create(
                    ConfigManager.getProperty(APPIUM_SERVER_URL_PROPERTY)
            ).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Appium server URL", e);
        }
    }
}