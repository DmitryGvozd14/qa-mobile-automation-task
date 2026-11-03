package com.qa.mobile.config;

public record DeviceConfig(
    String udid,
    String platformVersion,
    int systemPort
) {
}