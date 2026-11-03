package com.qa.mobile.driver.android;

import com.qa.mobile.config.ConfigManager;
import com.qa.mobile.config.DeviceConfig;
import com.qa.mobile.driver.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class AndroidDriverFactory implements DriverFactory {

  private static final String PLATFORM_NAME = "Android";
  private static final String AUTOMATION_NAME = "UiAutomator2";
  private static final String CAPABILITY_PLATFORM_NAME = "platformName";
  private static final String CAPABILITY_PLATFORM_VERSION = "platformVersion";
  private static final String CAPABILITY_UDID = "udid";
  private static final String CAPABILITY_AUTOMATION_NAME = "automationName";
  private static final String CAPABILITY_SYSTEM_PORT = "systemPort";
  private static final String CAPABILITY_APP_PACKAGE = "appPackage";
  private static final String APPIUM_SERVER_URL_PROPERTY = "appium.server.url";
  private static final String APP_PACKAGE_PROPERTY = "app.package";

  @Override
  public AndroidDriver createDriver(DeviceConfig deviceConfig) {
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability(CAPABILITY_PLATFORM_NAME, PLATFORM_NAME);
    capabilities.setCapability(
        CAPABILITY_PLATFORM_VERSION, deviceConfig.platformVersion());
    capabilities.setCapability(CAPABILITY_UDID, deviceConfig.udid());
    capabilities.setCapability(CAPABILITY_AUTOMATION_NAME, AUTOMATION_NAME);
    capabilities.setCapability(CAPABILITY_SYSTEM_PORT, deviceConfig.systemPort());
    capabilities.setCapability(
        CAPABILITY_APP_PACKAGE,
        ConfigManager.getProperty(APP_PACKAGE_PROPERTY));

    return new AndroidDriver(getServerUrl(), capabilities);
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