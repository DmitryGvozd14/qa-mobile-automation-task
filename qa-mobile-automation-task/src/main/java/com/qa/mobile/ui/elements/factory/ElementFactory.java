package com.qa.mobile.ui.elements.factory;

import com.qa.mobile.ui.elements.interfaces.Button;
import com.qa.mobile.ui.elements.interfaces.Label;
import com.qa.mobile.ui.elements.interfaces.TextField;
import org.openqa.selenium.By;

public interface ElementFactory {

  Button getButton(By locator, String name);

  TextField getTextField(By locator, String name);

  Label getLabel(By locator, String name);
}