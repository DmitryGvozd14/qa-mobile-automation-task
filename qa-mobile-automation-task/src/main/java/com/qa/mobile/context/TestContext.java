package com.qa.mobile.context;

import com.qa.mobile.config.DeviceConfig;

public final class TestContext {

  private static final ThreadLocal<TestContext> CONTEXT = new ThreadLocal<>();

  private DeviceConfig deviceConfig;

  private TestContext() {
  }

  public static void initialize() {
    CONTEXT.set(new TestContext());
  }

  public static TestContext getInstance() {
    TestContext context = CONTEXT.get();

    if (context == null) {
      throw new IllegalStateException(
          "Test context has not been initialized for the current thread");
    }

    return context;
  }

  public DeviceConfig getDeviceConfig() {
    return deviceConfig;
  }

  public void setDeviceConfig(DeviceConfig deviceConfig) {
    this.deviceConfig = deviceConfig;
  }

  public void clear() {
    CONTEXT.remove();
  }
}