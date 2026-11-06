package com.qa.mobile.testdata;

import com.qa.mobile.utils.StringUtils;
import java.security.SecureRandom;

public final class LoginDataGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private LoginDataGenerator() {
    }

    // Валидный логин случайной длины от 1 до максимальной
    public static String generateValidLogin() {
        int length = RANDOM.nextInt(
                ValidationRules.LOGIN_MIN_LENGTH,
                ValidationRules.LOGIN_MAX_LENGTH + 1
        );

        return generate(length);
    }

    // Валидный логин указанной длины
    public static String generateValidLogin(int length) {
        validateLength(length);

        return generate(length);
    }

    // Логин длиннее максимально допустимого на один символ
    public static String generateTooLongLogin() {
        return generate(ValidationRules.LOGIN_MAX_LENGTH + 1);
    }

    // Валидный логин с одним случайным неразрешённым символом
    public static String generateLoginWithDisallowedCharacter() {
        String login = generateValidLogin();

        int position = RANDOM.nextInt(login.length());
        char disallowedCharacter = generateDisallowedCharacter();

        return new StringBuilder(login)
                .insert(position, disallowedCharacter)
                .toString();
    }

    private static void validateLength(int length) {
        if (length < ValidationRules.LOGIN_MIN_LENGTH || length > ValidationRules.LOGIN_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Login length must be between %d and %d".formatted(ValidationRules.LOGIN_MIN_LENGTH,
                            ValidationRules.LOGIN_MAX_LENGTH)
            );
        }
    }

    private static String generate(int length) {
        return StringUtils.generateRandomString(length, ValidationRules.LOGIN_ALLOWED_CHARACTERS);
    }

    private static char generateDisallowedCharacter() {
        char character;

        do {
            character = (char) RANDOM.nextInt(Character.MAX_VALUE + 1);
        } while (
                ValidationRules.LOGIN_ALLOWED_CHARACTERS.indexOf(character) >= 0
                        || Character.isISOControl(character)
                        || Character.isSurrogate(character)
        );

        return character;
    }
}