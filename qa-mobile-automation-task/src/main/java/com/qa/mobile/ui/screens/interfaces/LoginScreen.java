package com.qa.mobile.ui.screens.interfaces;

public interface LoginScreen extends Screen {

  void enterUsername(String username);

  void enterPassword(String password);

  void tapLoginButton();

  boolean isErrorDisplayed();

  String getErrorMessage();
}