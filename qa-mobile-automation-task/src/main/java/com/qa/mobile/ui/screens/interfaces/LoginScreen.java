package com.qa.mobile.ui.screens.interfaces;

public interface LoginScreen extends Screen {

    void tapConfirmButton();

    String getErrorMessage();

    void togglePasswordVisibility();

    boolean isPasswordVisible();

    String getUsername();

    String getPassword();

    boolean isPasswordMasked();

    void enterUsername(String username, boolean needsClear);

    void enterUsername(String username);

    void pasteUsername(String username);

    void pasteUsername(String username, boolean needsClear);

    void enterPassword(String password, boolean needsClear);

    void enterPassword(String password);

    void pastePassword(String password);

    void pastePassword(String password, boolean needsClear);
}