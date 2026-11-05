package com.qa.mobile.steps;

import com.qa.mobile.enums.InputMethod;
import com.qa.mobile.ui.screens.interfaces.LoginScreen;

public class AuthorizationSteps {
  private final LoginScreen loginScreen;

  public AuthorizationSteps(LoginScreen loginScreen) {
    this.loginScreen = loginScreen;
  }

  public void enterLogin(String login, InputMethod inputMethod) {
    switch (inputMethod) {
      case TYPING -> loginScreen.enterUsername(login);
      case PASTING -> loginScreen.pasteUsername(login);
    }
  }

  public void enterPassword(String password, InputMethod inputMethod) {
    switch (inputMethod) {
      case TYPING -> loginScreen.enterPassword(password);
      case PASTING -> loginScreen.pastePassword(password);
    }
  }

  public void enterLogin(String login, InputMethod inputMethod, boolean needsClear) {
    switch (inputMethod) {
      case TYPING -> loginScreen.enterUsername(login, needsClear);
      case PASTING -> loginScreen.pasteUsername(login, needsClear);
    }
  }

  public void enterPassword(String password, InputMethod inputMethod, boolean needsClear) {
    switch (inputMethod) {
      case TYPING -> loginScreen.enterPassword(password, needsClear);
      case PASTING -> loginScreen.pastePassword(password, needsClear);
    }
  }
}
