package org.github.georgichochov.expsenses;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ExpenseTransaction {
  LocalDate postingDate();

  BigDecimal amount();

  String description();

  String category();

  String currency();
}
