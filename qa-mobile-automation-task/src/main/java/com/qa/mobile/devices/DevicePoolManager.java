package com.qa.mobile.devices;

public final class DevicePoolManager {

    private static final DevicePool DEVICE_POOL = new DevicePool();

    private DevicePoolManager() {
    }

    public static DevicePool getPool() {
        return DEVICE_POOL;
    }
}