package com.qa.mobile.ui.elements.android;

import com.qa.mobile.ui.elements.base.BaseElement;
import com.qa.mobile.ui.elements.interfaces.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AndroidButton extends BaseElement implements Button {

  public AndroidButton(By locator, String name) {
    super(locator, name);
  }

  @Override
  public void tap() {
    getWait().until(ExpectedConditions.elementToBeClickable(locator)).click();
  }
}