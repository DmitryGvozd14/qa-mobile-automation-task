package com.qa.mobile.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class DeviceConfigLoader {

    private static final String ANDROID_DEVICES_CONFIG = "devices/android.json";
    private static final String IOS_DEVICES_CONFIG = "devices/ios.json";
    private static final String DEVICES_PROPERTY = "devices";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private DeviceConfigLoader() {
    }

    public static List<DeviceConfig> loadDevices() {
        String configPath = getDevicesConfigPath();

        try (InputStream input = DeviceConfigLoader.class
            .getClassLoader()
            .getResourceAsStream(configPath)) {

            if (input == null) {
                throw new IllegalStateException("Device configuration file not found: " + configPath);
            }

            DeviceList deviceList = OBJECT_MAPPER.readValue(input, DeviceList.class);

            if (deviceList.devices() == null || deviceList.devices().isEmpty()) {
                throw new IllegalStateException("No devices configured in: " + configPath);
            }

            return deviceList.devices();

        } catch (IOException e) {
            throw new RuntimeException("Failed to load device configuration: " + configPath, e);
        }
    }

    private static String getDevicesConfigPath() {
        return switch (ConfigManager.getPlatform()) {
            case ANDROID -> ANDROID_DEVICES_CONFIG;
            case IOS -> IOS_DEVICES_CONFIG;
        };
    }

    private record DeviceList(List<DeviceConfig> devices) {
    }
}