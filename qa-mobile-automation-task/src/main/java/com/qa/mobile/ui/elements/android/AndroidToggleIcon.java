package com.qa.mobile.ui.elements.android;

import com.qa.mobile.ui.elements.interfaces.ToggleIcon;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AndroidToggleIcon extends AndroidButton implements ToggleIcon {

    private static final Logger LOGGER = LoggerFactory.getLogger(AndroidToggleIcon.class);

    public AndroidToggleIcon(By locator, String name) {
        super(locator, name);
    }

    @Override
    public boolean isChecked() {
        boolean checked = Boolean.parseBoolean(
                getElement().getAttribute("checked")
        );

        LOGGER.info("Toggle icon '{}' state: {}", name, checked ? "checked" : "unchecked");

        return checked;
    }
}
