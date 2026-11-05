package com.qa.mobile.ui.elements.interfaces;

public interface TextField {

  void enterText(String text);

  void pasteText(String text);

  void typeText(String text);

  boolean isMasked();

  void clearAndPaste(String text);

  void clearAndType(String text);

  void clear();

  String getText();

  String getValue();

  boolean isDisplayed();

  void waitUntilDisplayed();
}