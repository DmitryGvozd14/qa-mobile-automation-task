package com.qa.mobile.tests;

import com.qa.mobile.config.DeviceConfig;
import com.qa.mobile.context.TestContext;
import com.qa.mobile.devices.DevicePoolManager;
import com.qa.mobile.driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

  @BeforeMethod(alwaysRun = true)
  public void setUp() {
    TestContext.initialize();

    DeviceConfig deviceConfig =
        DevicePoolManager.getPool().acquireDevice();

    TestContext.getInstance().setDeviceConfig(deviceConfig);

    try {
      DriverManager.createDriver(deviceConfig);
    } catch (RuntimeException e) {
      DevicePoolManager.getPool().releaseDevice(deviceConfig);
      TestContext.getInstance().clear();
      throw e;
    }
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    DeviceConfig deviceConfig = TestContext.getInstance().getDeviceConfig();

    try {
      DriverManager.quitDriver();
    } finally {
      if (deviceConfig != null) {
        DevicePoolManager.getPool().releaseDevice(deviceConfig);
      }

      TestContext.getInstance().clear();
    }
  }
}