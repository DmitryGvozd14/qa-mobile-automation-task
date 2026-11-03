package com.qa.mobile.tests;

import com.qa.mobile.config.ConfigManager;
import com.qa.mobile.config.DeviceConfig;
import com.qa.mobile.config.DeviceConfigLoader;
import com.qa.mobile.ui.ScreenFactoryProvider;
import com.qa.mobile.ui.screens.interfaces.LoginScreen;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigManagerTest {

  @Test
  public void checkLoadConfiguration() {
    Assert.assertEquals(
        ConfigManager.getProperty("platform"),
        "ANDROID"
    );

    Assert.assertEquals(
        ConfigManager.getIntProperty("test.timeout.seconds"),
        10
    );
  }

  @Test
  public void checkLoadAndroidDevices() {
    List<DeviceConfig> devices = DeviceConfigLoader.loadDevices();

    Assert.assertEquals(devices.size(), 2);
    Assert.assertEquals(devices.get(0).udid(), "emulator-5554");
    Assert.assertEquals(devices.get(0).systemPort(), 8201);
  }

  @Test
  public void checkCreateLoginScreen() {
    LoginScreen loginScreen = ScreenFactoryProvider
        .getFactory()
        .getScreen(LoginScreen.class);

    Assert.assertNotNull(loginScreen);
  }
}