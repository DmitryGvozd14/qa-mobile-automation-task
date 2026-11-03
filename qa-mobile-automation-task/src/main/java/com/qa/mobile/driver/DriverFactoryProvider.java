package com.qa.mobile.driver;

import com.qa.mobile.config.ConfigManager;
import com.qa.mobile.config.Platform;
import com.qa.mobile.driver.android.AndroidDriverFactory;
import com.qa.mobile.driver.ios.IosDriverFactory;

public class DriverFactoryProvider {

  private DriverFactoryProvider() {
  }

  public static DriverFactory getFactory() {
    Platform platform = ConfigManager.getPlatform();

    return switch (platform) {
      case ANDROID -> new AndroidDriverFactory();
      case IOS -> new IosDriverFactory();
    };
  }
}