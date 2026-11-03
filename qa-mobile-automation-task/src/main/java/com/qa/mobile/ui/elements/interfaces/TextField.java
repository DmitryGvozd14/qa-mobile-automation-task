package com.qa.mobile.ui.elements.interfaces;

public interface TextField {

  void enterText(String text);

  void clear();

  String getText();

  boolean isDisplayed();

  void waitUntilDisplayed();

}