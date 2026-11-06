package com.qa.mobile.tests;

import com.qa.mobile.assertions.ScreenshotSoftAssert;
import com.qa.mobile.config.EnvironmentConfig;
import com.qa.mobile.constants.ValidationMessages;
import com.qa.mobile.enums.InputMethod;
import com.qa.mobile.steps.AuthorizationSteps;
import com.qa.mobile.testdata.LoginDataGenerator;
import com.qa.mobile.testdata.PasswordDataGenerator;
import com.qa.mobile.testdata.ValidationPatterns;
import com.qa.mobile.testdata.ValidationRules;
import com.qa.mobile.ui.ScreenFactoryProvider;
import com.qa.mobile.ui.screens.interfaces.LoginScreen;
import com.qa.mobile.ui.screens.interfaces.LoginSuccessScreen;
import com.qa.mobile.utils.StringUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("Mobile application")
@Feature("Authorization")
public class AuthorizationTests extends BaseTest {

    private static final String VALID_USERNAME = EnvironmentConfig.getRequiredEnv("A_TEST_LOGIN");
    private static final String VALID_PASSWORD = EnvironmentConfig.getRequiredEnv("A_TEST_PASSWORD");

    @Test(description = "TC-01: Successful authorization")
    @Description("Verify that a user can successfully authorize using valid credentials")
    public void successfulAuthorizationTest() {
        LoginScreen loginScreen = ScreenFactoryProvider.getFactory().getScreen(LoginScreen.class);
        LoginSuccessScreen loginSuccessScreen = ScreenFactoryProvider.getFactory().getScreen(LoginSuccessScreen.class);

        Allure.step("1. Verify that the login screen is displayed", () ->
                Assert.assertTrue(loginScreen.isDisplayed(), "[Step 1] Login screen is not displayed")
        );

        Allure.step("2. Verify that the username and password fields are empty", () -> {
            Assert.assertEquals(loginScreen.getUsername(), "",
                    "[Step 2] Username field should be empty by default");
            Assert.assertEquals(loginScreen.getPassword(), "",
                    "[Step 2] Password field should be empty by default");
        });

        Allure.step("3. Enter valid credentials", () -> {
            loginScreen.enterUsername(VALID_USERNAME);
            loginScreen.enterPassword(VALID_PASSWORD);
        });

        Allure.step("4. Tap the login button", loginScreen::tapConfirmButton);

        // TODO: Добавить проверку вызова authorize.
        // В текущей реализации приложения сетевой запрос отсутствует:
        // RemoteDataSourceProtocol выполняет локальное сравнение логина и пароля.
        // После реализации взаимодействия с backend добавить проверку отправки запроса.

        Allure.step("5. Verify successful authorization", () -> {
            loginSuccessScreen.waitUntilDisplayed();
            Assert.assertTrue(loginSuccessScreen.isDisplayed(),
                    "[Step 5] Successful authorization screen is not displayed");
        });
    }

    @DataProvider(name = "loginValidationData")
    public Object[][] loginValidationData() {
        return new Object[][]{
                {InputMethod.TYPING, ValidationMessages.LOGIN_INVALID_VALUE},
                {InputMethod.PASTING, ValidationMessages.LOGIN_EXCEPT_VALUE}
        };
    }

    @DataProvider(name = "inputMethodData")
    public Object[][] inputMethodData() {
        return new Object[][]{
                {InputMethod.TYPING},
                {InputMethod.PASTING}
        };
    }

    @Test(description = "TC-02: Login validation for disallowed characters",
            dataProvider = "loginValidationData")
    @Description("Verify login field behavior when entering disallowed characters using typing and pasting")
    public void loginValidationTest(InputMethod inputMethod, String expectedErrorMessage) {
        // arrange
        LoginScreen loginScreen = ScreenFactoryProvider.getFactory().getScreen(LoginScreen.class);
        AuthorizationSteps authorizationSteps = new AuthorizationSteps(loginScreen);
        String invalidLogin = LoginDataGenerator.generateLoginWithDisallowedCharacter();
        String expectedLogin = StringUtils.filterAllowedCharacters(invalidLogin,
                ValidationRules.LOGIN_ALLOWED_CHARACTERS);
        ScreenshotSoftAssert softAssert = new ScreenshotSoftAssert();

        // act
        Allure.step("1. Enter login with disallowed character", () ->
                authorizationSteps.enterLogin(invalidLogin, inputMethod));

        Allure.step("2. Verify that disallowed characters are removed", () -> {
            softAssert.assertEquals(loginScreen.getUsername(), expectedLogin,
                    "[Step 2] Login field should contain only allowed characters");
            softAssert.assertTrue(ValidationPatterns.LOGIN_PATTERN
                            .matcher(loginScreen.getUsername())
                            .matches(),
                    "[Step 2] Login field value does not match the required pattern");
        });

        Allure.step("3. Tap the login button", loginScreen::tapConfirmButton);

        Allure.step("4. Verify validation error message", () ->
                softAssert.assertEquals(loginScreen.getErrorMessage(), expectedErrorMessage,
                        String.format("[Step 4] Unexpected validation error message for %s input", inputMethod)));

        // assert
        softAssert.assertAll();
    }

    // TODO: При необходимости дополнить валидации пароля.
    // В спецификации указано, что разрешены все символы,
    // но одновременно описано поведение при вводе и вставке неразрешённых символов.
    // При необходимости добавить проверки PasswordInvalidValue и PasswordExceptValue.
    @Test(description = "TC-03: Password input and masking", dataProvider = "inputMethodData")
    @Description("Verify password input using typing and pasting, and ensure that the entered password is masked")
    public void passwordInputTest(InputMethod inputMethod) {
        // arrange
        LoginScreen loginScreen = ScreenFactoryProvider.getFactory().getScreen(LoginScreen.class);
        AuthorizationSteps authorizationSteps = new AuthorizationSteps(loginScreen);
        String password = PasswordDataGenerator.generateValidPassword();
        ScreenshotSoftAssert softAssert = new ScreenshotSoftAssert();

        // act
        Allure.step("1. Enter password", () ->
                authorizationSteps.enterPassword(password, inputMethod));

        // assert
        Allure.step("2. Verify that the password is entered correctly", () ->
                softAssert.assertEquals(loginScreen.getPassword(), password,
                        "[Step 2] Password field value does not match the entered password")
        );

        Allure.step("2. Verify that the password is entered correctly", () -> {
            softAssert.assertEquals(loginScreen.getPassword(), password,
                    "[Step 2] Password field value does not match the entered password");
            softAssert.assertTrue(ValidationPatterns.PASSWORD_PATTERN
                            .matcher(loginScreen.getPassword())
                            .matches(),
                    "[Step 2] Password field value does not match the required pattern");
        });

        Allure.step("3. Verify that the password is masked", () ->
                softAssert.assertTrue(loginScreen.isPasswordMasked(), "[Step 3] Password should be masked")
        );

        softAssert.assertAll();
    }

    @Test(description = "TC-04: Maximum allowed field length", dataProvider = "inputMethodData")
    @Description("Verify maximum allowed login and password lengths when entering text using typing and pasting")
    public void maximumFieldLengthTest(InputMethod inputMethod) {
        // arrange
        LoginScreen loginScreen = ScreenFactoryProvider.getFactory().getScreen(LoginScreen.class);
        AuthorizationSteps authorizationSteps = new AuthorizationSteps(loginScreen);
        ScreenshotSoftAssert softAssert = new ScreenshotSoftAssert();

        String validLogin = LoginDataGenerator.generateValidLogin(ValidationRules.LOGIN_MAX_LENGTH);
        String tooLongLogin = LoginDataGenerator.generateTooLongLogin();
        String validPassword = PasswordDataGenerator.generateValidPassword(ValidationRules.PASSWORD_MAX_LENGTH);
        String tooLongPassword = PasswordDataGenerator.generateTooLongPassword();

        // act
        Allure.step("1. Enter login with maximum allowed length", () ->
                authorizationSteps.enterLogin(validLogin, inputMethod, true)
        );

        Allure.step("2. Verify login with maximum allowed length", () -> {
            softAssert.assertEquals(loginScreen.getUsername(), validLogin,
                    "[Step 2] Login field should contain the complete input when its length equals the maximum allowed length"
            );
            softAssert.assertEquals(loginScreen.getUsername().length(), ValidationRules.LOGIN_MAX_LENGTH,
                    "[Step 2] Login field length should equal the maximum allowed length"
            );
        });

        Allure.step("3. Enter login exceeding maximum allowed length", () ->
                authorizationSteps.enterLogin(tooLongLogin, inputMethod, true)
        );

        Allure.step("4. Verify login does not exceed maximum allowed length", () -> {
            String actualLogin = loginScreen.getUsername();

            softAssert.assertTrue(actualLogin.length() <= ValidationRules.LOGIN_MAX_LENGTH,
                    String.format(
                            "[Step 4] Login field length exceeds the maximum allowed length. "
                                    + "Expected at most %d characters, but found %d",
                            ValidationRules.LOGIN_MAX_LENGTH, actualLogin.length()));

            softAssert.assertTrue(
                    ValidationPatterns.LOGIN_PATTERN.matcher(actualLogin).matches(),
                    "[Step 4] Login field value does not match the required pattern");
        });

        Allure.step("5. Enter password with maximum allowed length", () ->
                authorizationSteps.enterPassword(validPassword, inputMethod, true)
        );

        Allure.step("6. Verify password with maximum allowed length", () -> {
            softAssert.assertEquals(loginScreen.getPassword(), validPassword,
                    "[Step 6] Password field should contain the complete input when its length equals "
                            + "the maximum allowed length"
            );
            softAssert.assertEquals(loginScreen.getPassword().length(), ValidationRules.PASSWORD_MAX_LENGTH,
                    "[Step 6] Password field length should equal the maximum allowed length");
        });

        Allure.step("7. Enter password exceeding maximum allowed length", () ->
                authorizationSteps.enterPassword(tooLongPassword, inputMethod, true)
        );

        Allure.step("8. Verify password does not exceed maximum allowed length", () -> {
            String actualPassword = loginScreen.getPassword();

            softAssert.assertTrue(
                    actualPassword.length() <= ValidationRules.PASSWORD_MAX_LENGTH,
                    String.format(
                            "[Step 8] Password field length exceeds the maximum allowed length. "
                                    + "Expected at most %d characters, but found %d",
                            ValidationRules.PASSWORD_MAX_LENGTH, actualPassword.length()));

            softAssert.assertTrue(
                    ValidationPatterns.PASSWORD_PATTERN.matcher(actualPassword).matches(),
                    "[Step 8] Password field value does not match the required pattern");
        });

        // assert
        softAssert.assertAll();
    }

    @Test(description = "TC-05: Password visibility toggle")
    @Description("Verify that password visibility can be toggled between masked and visible states")
    public void passwordVisibilityToggleTest() {
        // arrange
        LoginScreen loginScreen = ScreenFactoryProvider.getFactory().getScreen(LoginScreen.class);
        AuthorizationSteps authorizationSteps = new AuthorizationSteps(loginScreen);
        ScreenshotSoftAssert softAssert = new ScreenshotSoftAssert();
        String password = PasswordDataGenerator.generateValidPassword();

        // act
        Allure.step("1. Verify password is masked by default", () -> {
            softAssert.assertFalse(loginScreen.isPasswordVisible(),
                    "[Step 1] Password visibility toggle should be unchecked by default"
            );
            softAssert.assertTrue(loginScreen.isPasswordMasked(), "[Step 1] Password should be masked by default"
            );
        });

        Allure.step("2. Enter valid password", () ->
                authorizationSteps.enterPassword(password, InputMethod.TYPING)
        );

        Allure.step("3. Verify password is masked after input", () -> {
            softAssert.assertFalse(loginScreen.isPasswordVisible(),
                    "[Step 3] Password visibility toggle should remain unchecked");
            softAssert.assertTrue(loginScreen.isPasswordMasked(), "[Step 3] Password should remain masked");
        });

        Allure.step("4. Show password", loginScreen::togglePasswordVisibility);

        Allure.step("5. Verify password is visible", () -> {
            softAssert.assertTrue(loginScreen.isPasswordVisible(),
                    "[Step 5] Password visibility toggle should be checked");
            softAssert.assertFalse(loginScreen.isPasswordMasked(), "[Step 5] Password should be visible");
        });

        Allure.step("6. Hide password", loginScreen::togglePasswordVisibility);

        Allure.step("7. Verify password is masked again", () -> {
            softAssert.assertFalse(loginScreen.isPasswordVisible(),
                    "[Step 7] Password visibility toggle should be unchecked");
            softAssert.assertTrue(loginScreen.isPasswordMasked(), "[Step 7] Password should be masked again");
        });

        Allure.step("8. Show password again", loginScreen::togglePasswordVisibility);

        Allure.step("9. Verify password is visible again", () -> {
            softAssert.assertTrue(loginScreen.isPasswordVisible(),
                    "[Step 9] Password visibility toggle should be checked");
            softAssert.assertFalse(loginScreen.isPasswordMasked(), "[Step 9] Password should be visible again");
        });

        // assert
        softAssert.assertAll();
    }
}
