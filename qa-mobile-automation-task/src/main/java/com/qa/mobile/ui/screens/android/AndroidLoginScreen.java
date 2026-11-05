package com.qa.mobile.ui.screens.android;

import com.qa.mobile.config.ConfigManager;
import com.qa.mobile.ui.elements.interfaces.ToggleIcon;
import com.qa.mobile.ui.screens.base.BaseScreen;
import com.qa.mobile.ui.screens.interfaces.LoginScreen;
import com.qa.mobile.ui.elements.interfaces.Button;
import com.qa.mobile.ui.elements.interfaces.Label;
import com.qa.mobile.ui.elements.interfaces.TextField;
import io.appium.java_client.AppiumBy;

public class AndroidLoginScreen extends BaseScreen implements LoginScreen {

  private final TextField loginField = getElementFactory().getTextField(AppiumBy.id("%s:id/etUsername".formatted(
          ConfigManager.getAppPackage())), "Login field");

  private final TextField passwordField = getElementFactory().getTextField(AppiumBy.id("%s:id/etPassword".formatted(
          ConfigManager.getAppPackage())), "Password field");

  private final Button confirmButton = getElementFactory().getButton(AppiumBy.id("%s:id/btnConfirm".formatted(
      ConfigManager.getAppPackage())), "Confirm button");

  private final Label errorLabel = getElementFactory().getLabel(AppiumBy.id("%s:id/tvError".formatted(
      ConfigManager.getAppPackage())), "Error message");

  private final ToggleIcon showPasswordToggleIcon = getElementFactory().getToggleIcon(AppiumBy.id("%s:id/text_input_end_icon".formatted(
      ConfigManager.getAppPackage())), "Show Password button");

  public AndroidLoginScreen() {
    super(AppiumBy.xpath("//*[@resource-id='%s:id/tvTitle' and @text='Вход в Alfa-Test']".formatted(ConfigManager.getAppPackage())), "Login screen");
  }

  @Override
  public void enterUsername(String username) {
    //loginField.enterText(username);
    loginField.typeText(username);
  }

  @Override
  public void enterPassword(String password) {
    passwordField.enterText(password);
  }

  @Override
  public void tapConfirmButton() {
    confirmButton.tap();
  }

  @Override
  public String getErrorMessage() {
    return errorLabel.getText();
  }

  @Override
  public void togglePasswordVisibility() {
    showPasswordToggleIcon.tap();
  }

  @Override
  public boolean isPasswordVisible() {
    return showPasswordToggleIcon.isChecked();
  }

  @Override
  public String getUsername() {
    return loginField.getValue();
  }

  @Override
  public String getPassword() {
    return passwordField.getValue();
  }

  @Override
  public void pasteUsername(String username) {
    loginField.pasteText(username);
  }

  @Override
  public void pasteUsername(String username, boolean needsClear) {
    if (needsClear) {
      loginField.clearAndPaste(username);
    } else {
      loginField.pasteText(username);
    }
  }

  @Override
  public void pastePassword(String password) {
    passwordField.pasteText(password);
  }

  @Override
  public void pastePassword(String password, boolean needsClear) {
    if (needsClear) {
      passwordField.clearAndPaste(password);
    } else {
      passwordField.pasteText(password);
    }
  }

  @Override
  public boolean isPasswordMasked() {
    return passwordField.isMasked();
  }

  @Override
  public void enterUsername(String username, boolean needsClear) {
    if (needsClear) {
      loginField.clear();
    }
    loginField.typeText(username);
  }

  @Override
  public void enterPassword(String password, boolean needsClear) {
    if (needsClear) {
      passwordField.clear();
    }
    passwordField.typeText(password);
  }

}