package org.github.georgichochov.expsenses.revolut;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.github.georgichochov.expsenses.ExpenseTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public record RevolutTransaction(LocalDate postingDate,
                                 BigDecimal amount,
                                 String description,
                                 String category,
                                 String currency) implements ExpenseTransaction {

  private static final Logger LOG = LogManager.getLogger(RevolutTransaction.class);
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("\"MMM dd, yyyy\"");

  public static RevolutTransaction parse(final String completedDate,
                                         final String amountIn,
                                         final String amountOut,
                                         final String description,
                                         final String category,
                                         final String currency) {
    var amount = parseAmount(amountIn, amountOut);
    return new RevolutTransaction(
        parseDate(completedDate),
        amount,
        description,
        category,
        currency
    );
  }

  private static BigDecimal parseAmount(final String amountIn, final String amountOut) {
    return parseAmount(Optional.ofNullable(amountIn.isBlank() ? null : amountIn).orElseGet(() -> "-" + amountOut));
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

}
