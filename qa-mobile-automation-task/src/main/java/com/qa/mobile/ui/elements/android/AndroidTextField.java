package com.qa.mobile.ui.elements.android;

import com.qa.mobile.ui.elements.base.BaseElement;
import com.qa.mobile.ui.elements.interfaces.TextField;
import org.openqa.selenium.By;

public class AndroidTextField extends BaseElement implements TextField {

  public AndroidTextField(By locator, String name) {
    super(locator, name);
  }

  @Override
  public void enterText(String text) {
    var element = getElement();
    element.clear();
    element.sendKeys(text);
  }

  @Override
  public void clear() {
    getElement().clear();
  }
}