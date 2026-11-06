package com.qa.mobile.config;

public record DeviceConfig(
        String udid,
        String deviceName,
        String platform,
        String platformVersion,
        int systemPort
) {
}