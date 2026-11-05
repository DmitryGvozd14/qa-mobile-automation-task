package com.qa.mobile.utils;

public final class StringUtils {

  private StringUtils() {
  }

  public static String filterAllowedCharacters(String value, String allowedCharacters) {
    StringBuilder result = new StringBuilder();

    value.codePoints()
        .filter(character -> allowedCharacters.indexOf(character) >= 0)
        .forEach(result::appendCodePoint);

    return result.toString();
  }
}
