package com.qa.mobile.ui.screens.configuration;

import com.qa.mobile.ui.screens.android.AndroidLoginScreen;
import com.qa.mobile.ui.screens.interfaces.LoginScreen;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.DefaultPicoContainer;

public final class AndroidScreenModule {

  private AndroidScreenModule() {}

  public static MutablePicoContainer createContainer() {
    return new DefaultPicoContainer().addComponent(LoginScreen.class, AndroidLoginScreen.class);
  }
}