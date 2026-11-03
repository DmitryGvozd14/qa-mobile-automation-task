package com.qa.mobile.listeners;

import com.qa.mobile.config.DeviceConfigLoader;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class TestExecutionListener implements ISuiteListener {

  private static final String THREAD_COUNT_PARAMETER = "thread-count";

  @Override
  public void onStart(ISuite suite) {
    int deviceCount = DeviceConfigLoader.loadDevices().size();

    suite.getXmlSuite().setThreadCount(deviceCount);
  }
}