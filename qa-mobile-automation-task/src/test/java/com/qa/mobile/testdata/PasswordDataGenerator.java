package com.qa.mobile.testdata;

import java.security.SecureRandom;

public final class PasswordDataGenerator {

  private static final SecureRandom RANDOM = new SecureRandom();

  private PasswordDataGenerator() {
  }

  public static String generateValidPassword() {
    int length = RANDOM.nextInt(
        ValidationRules.PASSWORD_MIN_LENGTH,
        ValidationRules.PASSWORD_MAX_LENGTH + 1
    );

    return generate(length);
  }

  public static String generateValidPassword(int length) {
    validateLength(length);

    return generate(length);
  }

  public static String generateTooLongPassword() {
    return generate(ValidationRules.PASSWORD_MAX_LENGTH + 1);
  }

  private static void validateLength(int length) {
    if (length < ValidationRules.PASSWORD_MIN_LENGTH || length > ValidationRules.PASSWORD_MAX_LENGTH) {
      throw new IllegalArgumentException(
          "Password length must be between %d and %d".formatted(ValidationRules.PASSWORD_MIN_LENGTH,
              ValidationRules.PASSWORD_MAX_LENGTH)
      );
    }
  }

  private static String generate(int length) {
    StringBuilder result = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int randomIndex = RANDOM.nextInt(
          ValidationRules.PASSWORD_ALLOWED_CHARACTERS.length()
      );

      result.append(
          ValidationRules.PASSWORD_ALLOWED_CHARACTERS.charAt(randomIndex)
      );
    }

    return result.toString();
  }
}