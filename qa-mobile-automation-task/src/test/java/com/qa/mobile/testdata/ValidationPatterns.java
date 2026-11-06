package com.qa.mobile.testdata;

import java.util.regex.Pattern;

public final class ValidationPatterns {

    private ValidationPatterns() {
    }

    public static final String LOGIN_REGEX = String.format(
            "^[%s]{%d,%d}$",
            escapeCharacterClass(),
            ValidationRules.LOGIN_MIN_LENGTH,
            ValidationRules.LOGIN_MAX_LENGTH
    );

    public static final String PASSWORD_REGEX = String.format(
            "^[\\s\\S]{%d,%d}$",
            ValidationRules.PASSWORD_MIN_LENGTH,
            ValidationRules.PASSWORD_MAX_LENGTH
    );

    public static final Pattern LOGIN_PATTERN = Pattern.compile(LOGIN_REGEX);

    public static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    private static String escapeCharacterClass() {
        return ValidationRules.LOGIN_ALLOWED_CHARACTERS
                .replace("\\", "\\\\")
                .replace("]", "\\]")
                .replace("^", "\\^")
                .replace("-", "\\-");
    }
}