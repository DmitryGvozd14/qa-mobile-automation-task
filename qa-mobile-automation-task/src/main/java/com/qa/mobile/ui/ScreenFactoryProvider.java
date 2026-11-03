package com.qa.mobile.ui;

import com.qa.mobile.config.ConfigManager;
import com.qa.mobile.ui.screens.configuration.AndroidScreenModule;
import com.qa.mobile.ui.screens.factory.ScreenFactory;
import org.picocontainer.MutablePicoContainer;

public final class ScreenFactoryProvider {

  private static final ScreenFactory SCREEN_FACTORY = createFactory();

  private ScreenFactoryProvider() {}

  private static ScreenFactory createFactory() {
    MutablePicoContainer container = switch (ConfigManager.getPlatform()) {
      case ANDROID -> AndroidScreenModule.createContainer();
      case IOS -> throw new UnsupportedOperationException(
          "iOS platform is not implemented yet");
    };

    return new ScreenFactory(container);
  }

  public static ScreenFactory getFactory() {
    return SCREEN_FACTORY;
  }
}