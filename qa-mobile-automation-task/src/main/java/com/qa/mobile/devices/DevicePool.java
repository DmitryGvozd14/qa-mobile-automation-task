package com.qa.mobile.devices;

import com.qa.mobile.config.DeviceConfig;
import com.qa.mobile.config.DeviceConfigLoader;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevicePool {

    private final BlockingQueue<DeviceConfig> availableDevices;
    private static final Logger LOGGER = LoggerFactory.getLogger(DevicePool.class);

    public DevicePool() {
        List<DeviceConfig> devices = DeviceConfigLoader.loadDevices();
        this.availableDevices = new LinkedBlockingQueue<>(devices);

        LOGGER.info("Device pool initialized with {} device(s)", devices.size());
    }

    public DeviceConfig acquireDevice() {
        try {
            DeviceConfig device = availableDevices.take();

            LOGGER.info("Device acquired: {} ({})", device.deviceName(), device.udid());

            return device;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            LOGGER.error("Interrupted while waiting for an available device", e);

            throw new RuntimeException("Interrupted while waiting for an available device", e);
        }
    }

    public void releaseDevice(DeviceConfig device) {
        availableDevices.add(device);

        LOGGER.info("Device released: {} ({})", device.deviceName(), device.udid());
    }
}