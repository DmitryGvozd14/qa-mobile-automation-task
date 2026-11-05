package com.qa.mobile.testdata;

import com.qa.mobile.models.TestUser;

public final class TestUserGenerator {

  private TestUserGenerator() {
  }

  public static TestUser generateValidUser() {
    return new TestUser(
        LoginDataGenerator.generateValidLogin(),
        PasswordDataGenerator.generateValidPassword()
    );
  }
}