package com.qa.mobile.ui.screens.configuration;

import com.qa.mobile.ui.screens.android.AndroidLoginScreen;
import com.qa.mobile.ui.screens.android.AndroidLoginSuccessScreen;
import com.qa.mobile.ui.screens.interfaces.LoginScreen;
import com.qa.mobile.ui.screens.interfaces.LoginSuccessScreen;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

public final class AndroidScreenModule {

    private AndroidScreenModule() {
    }

    public static MutablePicoContainer createContainer() {
        return new DefaultPicoContainer()
                .addComponent(LoginScreen.class, AndroidLoginScreen.class)
                .addComponent(LoginSuccessScreen.class, AndroidLoginSuccessScreen.class);
    }
}