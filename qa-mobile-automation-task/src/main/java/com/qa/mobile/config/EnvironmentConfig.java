package com.qa.mobile.config;

public final class EnvironmentConfig {

    private EnvironmentConfig() {
    }

    public static String getRequiredEnv(String name) {
        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    String.format("Environment variable '%s' is not set", name)
            );
        }

        return value;
    }
}
