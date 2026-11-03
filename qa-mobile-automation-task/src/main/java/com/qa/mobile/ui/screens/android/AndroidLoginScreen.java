package com.qa.mobile.ui.screens.android;

import com.qa.mobile.ui.screens.base.BaseScreen;
import com.qa.mobile.ui.screens.interfaces.LoginScreen;
import com.qa.mobile.ui.elements.interfaces.Button;
import com.qa.mobile.ui.elements.interfaces.Label;
import com.qa.mobile.ui.elements.interfaces.TextField;
import org.openqa.selenium.By;

public class AndroidLoginScreen extends BaseScreen implements LoginScreen {

  private final TextField usernameField = getElementFactory().getTextField(By.id("username"),
      "Username field");

  private final TextField passwordField = getElementFactory().getTextField(By.id("password"),
      "Password field");

  private final Button loginButton = getElementFactory().getButton(By.id("login"), "Login button");

  private final Label errorLabel = getElementFactory().getLabel(By.id("error"), "Error message");

  public AndroidLoginScreen() {
    super(By.id("login_screen"), "Login screen");
  }

  @Override
  public void enterUsername(String username) {
    usernameField.enterText(username);
  }

  @Override
  public void enterPassword(String password) {
    passwordField.enterText(password);
  }

  @Override
  public void tapLoginButton() {
    loginButton.tap();
  }

  @Override
  public boolean isErrorDisplayed() {
    return errorLabel.isDisplayed();
  }

  @Override
  public String getErrorMessage() {
    return errorLabel.getText();
  }
}