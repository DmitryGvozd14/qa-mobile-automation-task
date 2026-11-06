package com.qa.mobile.ui.elements.android;

import com.qa.mobile.ui.elements.base.BaseElement;
import com.qa.mobile.ui.elements.interfaces.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AndroidButton extends BaseElement implements Button {

    private static final Logger LOGGER = LoggerFactory.getLogger(AndroidButton.class);

    public AndroidButton(By locator, String name) {
        super(locator, name);
    }

    @Override
    public void tap() {
        LOGGER.info("Tapping button: {}", name);

        getWait().until(ExpectedConditions.elementToBeClickable(locator)).click();

        LOGGER.info("Button tapped successfully: {}", name);
    }
}