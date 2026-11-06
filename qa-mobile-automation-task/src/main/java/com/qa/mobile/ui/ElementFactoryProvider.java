package com.qa.mobile.ui;

import com.qa.mobile.config.ConfigManager;
import com.qa.mobile.ui.elements.factory.ElementFactory;
import com.qa.mobile.ui.elements.factory.android.AndroidElementFactory;

public final class ElementFactoryProvider {

    private static final ElementFactory ANDROID_FACTORY = new AndroidElementFactory();

    private ElementFactoryProvider() {
    }

    public static ElementFactory getFactory() {
        return switch (ConfigManager.getPlatform()) {
            case ANDROID -> ANDROID_FACTORY;
            case IOS -> throw new UnsupportedOperationException(
                    "iOS platform is not implemented yet"
            );
        };
    }
}