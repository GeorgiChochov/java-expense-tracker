package org.github.georgichochov.expsenses.nordea;

import org.github.georgichochov.expsenses.InputSanitizer;

public class NordeaInputSanitizer implements InputSanitizer {

  public String sanitize(final String input) {
    return InputSanitizer.super.sanitize(input)
        .replaceFirst("\uFEFF", "");
  }

}
