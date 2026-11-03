package com.qa.mobile.ui.elements.factory.android;

import com.qa.mobile.ui.elements.android.AndroidButton;
import com.qa.mobile.ui.elements.android.AndroidLabel;
import com.qa.mobile.ui.elements.android.AndroidTextField;
import com.qa.mobile.ui.elements.interfaces.Button;
import com.qa.mobile.ui.elements.interfaces.Label;
import com.qa.mobile.ui.elements.interfaces.TextField;
import com.qa.mobile.ui.elements.factory.ElementFactory;
import org.openqa.selenium.By;

public class AndroidElementFactory implements ElementFactory {

  @Override
  public Button getButton(By locator, String name) {
    return new AndroidButton(locator, name);
  }

  @Override
  public TextField getTextField(By locator, String name) {
    return new AndroidTextField(locator, name);
  }

  @Override
  public Label getLabel(By locator, String name) {
    return new AndroidLabel(locator, name);
  }
}