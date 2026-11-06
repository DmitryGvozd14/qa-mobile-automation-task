package com.qa.mobile.ui.screens.android;

import com.qa.mobile.ui.screens.base.BaseScreen;
import com.qa.mobile.ui.screens.interfaces.LoginSuccessScreen;
import io.appium.java_client.AppiumBy;

public class AndroidLoginSuccessScreen extends BaseScreen implements LoginSuccessScreen {

    public AndroidLoginSuccessScreen() {
        super(AppiumBy.xpath("//android.widget.TextView[@text='Вход в Alfa-Test выполнен']"),
                "Login Success screen");
    }
}
