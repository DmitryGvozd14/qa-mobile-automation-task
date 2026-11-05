package com.qa.mobile.tests;

import com.qa.mobile.constants.ValidationMessages;
import com.qa.mobile.enums.InputMethod;
import com.qa.mobile.steps.AuthorizationSteps;
import com.qa.mobile.testdata.LoginDataGenerator;
import com.qa.mobile.testdata.PasswordDataGenerator;
import com.qa.mobile.testdata.ValidationRules;
import com.qa.mobile.ui.ScreenFactoryProvider;
import com.qa.mobile.ui.screens.interfaces.LoginScreen;
import com.qa.mobile.ui.screens.interfaces.LoginSuccessScreen;
import com.qa.mobile.utils.StringUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Mobile application")
@Feature("Authorization")
public class AuthorizationTests extends BaseTest{
  private static final String VALID_USERNAME = "Login";
  private static final String VALID_PASSWORD = "Password";

  @Test(description = "Successful authorization")
  @Story("Successful login")
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify successful authorization with valid credentials")
  public void successfulAuthorizationTest() {
    LoginScreen loginScreen = ScreenFactoryProvider.getFactory().getScreen(LoginScreen.class);
    LoginSuccessScreen loginSuccessScreen = ScreenFactoryProvider.getFactory().getScreen(LoginSuccessScreen.class);

    Allure.step("Verify that the login screen is displayed", () ->
        Assert.assertTrue(loginScreen.isDisplayed(), "Login screen is not displayed")
    );

    Allure.step("Verify that the username and password fields are empty", () -> {
      Assert.assertEquals(loginScreen.getUsername(), "", "Username field should be empty by default");

      Assert.assertEquals(loginScreen.getPassword(), "", "Password field should be empty by default");
    });

    Allure.step("Enter valid credentials", () -> {
      loginScreen.enterUsername(VALID_USERNAME);
      loginScreen.enterPassword(VALID_PASSWORD);
    });

    Allure.step("Tap the login button", loginScreen::tapConfirmButton);

    Allure.step("Verify successful authorization", () -> {
      loginSuccessScreen.waitUntilDisplayed();
      Assert.assertTrue(loginSuccessScreen.isDisplayed(), "Successful authorization screen is not displayed");
    });
  }

  @DataProvider(name = "loginValidationData")
  public Object[][] loginValidationData() {
    return new Object[][]{
        {
            InputMethod.TYPING,
            ValidationMessages.LOGIN_INVALID_VALUE
        },
        {
            InputMethod.PASTING,
            ValidationMessages.LOGIN_EXCEPT_VALUE
        }
    };
  }

  @DataProvider(name = "inputMethodData")
  public Object[][] fieldLengthData() {
    return new Object[][]{
        {InputMethod.TYPING},
        {InputMethod.PASTING}
    };
  }

  @Test(description = "Login validation for disallowed characters", dataProvider = "loginValidationData")
  public void loginValidationTest(InputMethod inputMethod, String expectedErrorMessage) {
    //arrange
    LoginScreen loginScreen = ScreenFactoryProvider.getFactory().getScreen(LoginScreen.class);
    AuthorizationSteps authorizationSteps = new AuthorizationSteps(loginScreen);
    String invalidLogin = LoginDataGenerator.generateLoginWithDisallowedCharacter();
    String expectedLogin = StringUtils.filterAllowedCharacters(invalidLogin, ValidationRules.LOGIN_ALLOWED_CHARACTERS);
    SoftAssert softAssert = new SoftAssert();

    //act
    Allure.step("Enter login with disallowed character", () ->
        authorizationSteps.enterLogin(invalidLogin, inputMethod)
    );

    Allure.step("Verify that disallowed characters are removed", () ->
        softAssert.assertEquals(loginScreen.getUsername(), expectedLogin)
    );

    Allure.step("Tap the login button", loginScreen::tapConfirmButton);

    Allure.step("Verify validation error message", () ->
        softAssert.assertEquals(loginScreen.getErrorMessage(), expectedErrorMessage)
    );

    //assert
    softAssert.assertAll();
  }


  @Test(description = "Password input and masking", dataProvider = "inputMethodData")
  @Story("Password input")
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify password input using typing and pasting, and verify that the password is masked")
  public void passwordInputTest(InputMethod inputMethod) {
    // arrange
    LoginScreen loginScreen = ScreenFactoryProvider.getFactory().getScreen(LoginScreen.class);
    AuthorizationSteps authorizationSteps = new AuthorizationSteps(loginScreen);
    String password = PasswordDataGenerator.generateValidPassword();
    SoftAssert softAssert = new SoftAssert();

    // act
    Allure.step("Enter password", () ->
        authorizationSteps.enterPassword(password, inputMethod)
    );

    // assert
    Allure.step("Verify that the password is entered correctly", () ->
        softAssert.assertEquals(loginScreen.getPassword(), password,
            "Password field value does not match the entered password")
    );

    Allure.step("Verify that the password is masked", () ->
        softAssert.assertTrue(loginScreen.isPasswordMasked(), "Password should be masked")
    );

    softAssert.assertAll();
  }

  @Test(description = "Maximum allowed length for login and password", dataProvider = "inputMethodData")
  public void maximumFieldLengthTest(InputMethod inputMethod) {
    //arrange
    LoginScreen loginScreen = ScreenFactoryProvider.getFactory().getScreen(LoginScreen.class);
    AuthorizationSteps authorizationSteps = new AuthorizationSteps(loginScreen);
    SoftAssert softAssert = new SoftAssert();
    String validLogin = LoginDataGenerator.generateValidLogin(ValidationRules.LOGIN_MAX_LENGTH);
    String tooLongLogin = LoginDataGenerator.generateTooLongLogin();
    String validPassword = PasswordDataGenerator.generateValidPassword(ValidationRules.PASSWORD_MAX_LENGTH);
    String tooLongPassword = PasswordDataGenerator.generateTooLongPassword();

    //act
    Allure.step("Enter login with maximum allowed length", () ->
        authorizationSteps.enterLogin(validLogin, inputMethod, true));

    Allure.step("Verify login with maximum allowed length", () -> {
      softAssert.assertEquals(loginScreen.getUsername(), validLogin);
      softAssert.assertEquals(loginScreen.getUsername().length(), ValidationRules.LOGIN_MAX_LENGTH);
    });

    Allure.step("Enter login exceeding maximum allowed length", () ->
        authorizationSteps.enterLogin(tooLongLogin, inputMethod, true));

    Allure.step("Verify login does not exceed maximum allowed length", () ->
        softAssert.assertTrue(loginScreen.getUsername().length() <= ValidationRules.LOGIN_MAX_LENGTH));

    Allure.step("Enter password with maximum allowed length", () ->
        authorizationSteps.enterPassword(validPassword, inputMethod, true));

    Allure.step("Verify password with maximum allowed length", () -> {
      softAssert.assertEquals(loginScreen.getPassword(), validPassword);
      softAssert.assertEquals(loginScreen.getPassword().length(), ValidationRules.PASSWORD_MAX_LENGTH);
    });

    Allure.step("Enter password exceeding maximum allowed length", () ->
        authorizationSteps.enterPassword(tooLongPassword, inputMethod, true));

    Allure.step("Verify password does not exceed maximum allowed length", () ->
        softAssert.assertTrue(loginScreen.getPassword().length() <= ValidationRules.PASSWORD_MAX_LENGTH));

    //assert
    softAssert.assertAll();
  }

  @Test(description = "Password visibility toggle")
  public void passwordVisibilityToggleTest() {
    //arrange
    LoginScreen loginScreen = ScreenFactoryProvider.getFactory().getScreen(LoginScreen.class);
    AuthorizationSteps authorizationSteps = new AuthorizationSteps(loginScreen);
    SoftAssert softAssert = new SoftAssert();
    String password = PasswordDataGenerator.generateValidPassword();

    //act
    Allure.step("Verify password is masked by default", () -> {
      softAssert.assertFalse(loginScreen.isPasswordVisible(), "Password visibility toggle should be unchecked by default");
      softAssert.assertTrue(loginScreen.isPasswordMasked(), "Password should be masked by default");
    });

    Allure.step("Enter valid password", () -> authorizationSteps.enterPassword(password, InputMethod.TYPING));

    Allure.step("Verify password is masked after input", () -> {
      softAssert.assertFalse(loginScreen.isPasswordVisible(), "Password visibility toggle should remain unchecked");
      softAssert.assertTrue(loginScreen.isPasswordMasked(), "Password should remain masked");
    });

    Allure.step("Show password", loginScreen::togglePasswordVisibility);

    Allure.step("Verify password is visible", () -> {
      softAssert.assertTrue(loginScreen.isPasswordVisible(), "Password visibility toggle should be checked");
      softAssert.assertFalse(loginScreen.isPasswordMasked(), "Password should be visible");
    });

    Allure.step("Hide password", loginScreen::togglePasswordVisibility);

    Allure.step("Verify password is masked again", () -> {
      softAssert.assertFalse(loginScreen.isPasswordVisible(), "Password visibility toggle should be unchecked");
      softAssert.assertTrue(loginScreen.isPasswordMasked(), "Password should be masked again");
    });

    Allure.step("Show password again", loginScreen::togglePasswordVisibility);

    Allure.step("Verify password is visible again", () -> {
      softAssert.assertTrue(loginScreen.isPasswordVisible(), "Password visibility toggle should be checked");
      softAssert.assertFalse(loginScreen.isPasswordMasked(), "Password should be visible again");
    });

    //assert
    softAssert.assertAll();
  }
}
