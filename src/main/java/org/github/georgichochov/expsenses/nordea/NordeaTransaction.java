package org.github.georgichochov.expsenses.nordea;

import org.apache.logging.log4j.Logger;
import org.github.georgichochov.expsenses.ExpenseTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.apache.logging.log4j.LogManager.getLogger;

public record NordeaTransaction(LocalDate postingDate,
                                BigDecimal amount,
                                String description,
                                String category,
                                String currency) implements ExpenseTransaction {

  private static final Logger LOG = getLogger(NordeaTransaction.class);
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static NordeaTransaction parse(final String postingDate,
                                        final String amount,
                                        final String heading,
                                        final String currency) {
    return new NordeaTransaction(
        parseDate(postingDate),
        parseAmount(amount),
        heading,
        parseCategory(heading),
        currency
    );
  }

  private static LocalDate parseDate(final String postingDate) {
    try {
      return LocalDate.parse(postingDate, DATE_FORMAT);
    } catch (DateTimeParseException e) {
      LOG.warn("Failed to parse posting date", e);
      return LocalDate.now();
    }
  }

  private static BigDecimal parseAmount(final String amount) {
    try {
      return BigDecimal.valueOf(Double.parseDouble(amount.replace(',', '.')));
    } catch (NumberFormatException e) {
      LOG.error("Cannot parse amount", e);
      return BigDecimal.ZERO;
    }
  }

  private static String parseCategory(final String heading) {
    return heading;
  }

}
