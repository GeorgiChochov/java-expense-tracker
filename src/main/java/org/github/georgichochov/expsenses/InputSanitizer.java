package org.github.georgichochov.expsenses;

public interface InputSanitizer {

  String NON_NEW_LINE_WHITESPACE = "[\\t ]*";

  default String sanitize(final String input) {
    return input.replaceAll(NON_NEW_LINE_WHITESPACE + "," + NON_NEW_LINE_WHITESPACE, ",")
        .replaceAll("\u00a0", "");
  }

}
