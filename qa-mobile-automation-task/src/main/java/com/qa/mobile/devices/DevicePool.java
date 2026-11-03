package com.qa.mobile.devices;

import com.qa.mobile.config.DeviceConfig;
import com.qa.mobile.config.DeviceConfigLoader;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DevicePool {

    private final BlockingQueue<DeviceConfig> availableDevices;

    public DevicePool() {
        List<DeviceConfig> devices = DeviceConfigLoader.loadDevices();
        this.availableDevices = new LinkedBlockingQueue<>(devices);
    }

    public DeviceConfig acquireDevice() {
        try {
            return availableDevices.take();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for an available device", e);
        }
    }

    public void releaseDevice(DeviceConfig device) {
        availableDevices.add(device);
    }
}