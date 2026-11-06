package com.qa.mobile.utils;

import java.util.Random;

public final class StringUtils {

    private static final Random RANDOM = new Random();

    private StringUtils() {
    }

    public static String filterAllowedCharacters(String value, String allowedCharacters) {
        StringBuilder result = new StringBuilder();

        value.codePoints()
                .filter(character -> allowedCharacters.indexOf(character) >= 0)
                .forEach(result::appendCodePoint);

        return result.toString();
    }

    public static String generateRandomString(int length, String allowedCharacters) {
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(allowedCharacters.length());
            result.append(allowedCharacters.charAt(randomIndex));
        }

        return result.toString();
    }
}
