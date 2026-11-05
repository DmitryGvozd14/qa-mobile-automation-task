package com.qa.mobile.ui.elements.android;

import com.qa.mobile.ui.elements.interfaces.ToggleIcon;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AndroidToggleIcon extends AndroidButton implements ToggleIcon {

  public AndroidToggleIcon(By locator, String name) {
    super(locator, name);
  }

  @Override
  public void tap() {
    getWait().until(ExpectedConditions.elementToBeClickable(locator)).click();
  }

  @Override
  public boolean isChecked() {
    return Boolean.parseBoolean(getElement().getAttribute("checked"));
  }
}
