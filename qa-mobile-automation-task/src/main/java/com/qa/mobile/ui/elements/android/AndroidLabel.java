package com.qa.mobile.ui.elements.android;

import com.qa.mobile.ui.elements.base.BaseElement;
import com.qa.mobile.ui.elements.interfaces.Label;
import org.openqa.selenium.By;

public class AndroidLabel extends BaseElement implements Label {

    public AndroidLabel(By locator, String name) {
        super(locator, name);
    }
}