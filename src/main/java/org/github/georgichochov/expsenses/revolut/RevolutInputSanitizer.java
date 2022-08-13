package org.github.georgichochov.expsenses.revolut;

import org.github.georgichochov.expsenses.InputSanitizer;

import java.util.regex.Pattern;

public class RevolutInputSanitizer implements InputSanitizer {

  public String sanitize(final String input) {
    final var thousandsSeparatorSanitized = Pattern
        .compile(" (\\d+),(\\d+).(\\d+)")
        .matcher(input)
        .replaceAll(m -> m.group(1) + m.group(2) + "." + m.group(3));
    return InputSanitizer.super.sanitize(thousandsSeparatorSanitized);
  }

}
