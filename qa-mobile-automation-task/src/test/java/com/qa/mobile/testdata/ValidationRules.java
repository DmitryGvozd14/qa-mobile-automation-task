package com.qa.mobile.testdata;

public final class ValidationRules {

  private ValidationRules() {
  }

  public static final String LOGIN_ALLOWED_CHARACTERS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,/'_ -";

  public static final String PASSWORD_ALLOWED_CHARACTERS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-={}[]:;'<>,.?/";


  public static final int LOGIN_MIN_LENGTH = 1;
  public static final int LOGIN_MAX_LENGTH = 50;

  public static final int PASSWORD_MIN_LENGTH = 1;
  public static final int PASSWORD_MAX_LENGTH = 50;
}