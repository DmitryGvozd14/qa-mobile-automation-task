package com.qa.mobile.tests;

import com.qa.mobile.config.DeviceConfig;
import com.qa.mobile.context.TestContext;
import com.qa.mobile.devices.DevicePoolManager;
import com.qa.mobile.driver.DriverManager;
import io.qameta.allure.Allure;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    private static final String TEST_ID_KEY = "testId";
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        String testId = String.format("%s-%s", method.getName(), UUID.randomUUID());

        MDC.put(TEST_ID_KEY, testId);
        LOGGER.info("Starting test: {} (ID: {})", method.getName(), testId);
        TestContext.initialize();

        DeviceConfig deviceConfig = null;

        try {
            deviceConfig = DevicePoolManager.getPool().acquireDevice();

            TestContext.getInstance().setDeviceConfig(deviceConfig);

            LOGGER.info("Test is running on device: {} ({}), platform: {}, v. {}",
                    deviceConfig.udid(),
                    deviceConfig.deviceName(),
                    deviceConfig.platform(),
                    deviceConfig.platformVersion()
            );

            DriverManager.createDriver(deviceConfig);

        } catch (RuntimeException e) {
            if (deviceConfig != null) {
                DevicePoolManager.getPool().releaseDevice(deviceConfig);
            }

            TestContext.getInstance().clear();
            MDC.remove(TEST_ID_KEY);

            throw e;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DeviceConfig deviceConfig = TestContext.getInstance().getDeviceConfig();

        try {
            DriverManager.quitDriver();
        } finally {
            try {
                if (deviceConfig != null) {
                    DevicePoolManager.getPool().releaseDevice(deviceConfig);
                }
            } finally {
                TestContext.getInstance().clear();
                LOGGER.info("Test teardown completed");
                attachTestLog();
                MDC.remove(TEST_ID_KEY);
            }
        }
    }

    private void attachTestLog() {
        String testId = MDC.get(TEST_ID_KEY);

        if (testId == null) {
            return;
        }

        Path logPath = Path.of("target", "logs", testId + ".log");

        try {
            Allure.addAttachment(
                    "Test execution log",
                    "text/plain",
                    Files.newInputStream(logPath),
                    ".log"
            );
        } catch (IOException e) {
            LOGGER.error("Failed to attach test log: {}", logPath, e);
        }
    }
}