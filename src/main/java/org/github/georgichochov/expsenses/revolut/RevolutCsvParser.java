package org.github.georgichochov.expsenses.revolut;

import de.siegmar.fastcsv.reader.NamedCsvReader;
import org.apache.logging.log4j.Logger;
import org.github.georgichochov.expsenses.ExpenseTransaction;

import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.LogManager.getLogger;

public class RevolutCsvParser {

  private static final Logger LOG = getLogger(RevolutCsvParser.class);

  private final RevolutInputSanitizer inputSanitizer;

  public RevolutCsvParser(final RevolutInputSanitizer inputSanitizer) {
    this.inputSanitizer = inputSanitizer;
  }

  public List<? extends ExpenseTransaction> parseTransactions(final String input) {
    return parse(inputSanitizer.sanitize(input));
  }

  public List<? extends ExpenseTransaction> parse(final String input) {
    return NamedCsvReader.builder()
        .build(input)
        .stream().map(row -> {
          var date = row.getField("Completed Date");
          var description = row.getField("Description");
          var category = row.getField("Category");
          var amounts = row.getFields().entrySet().stream().filter(entry ->
              entry.getKey().startsWith("Paid")
          ).toList();
          var currency = amounts.stream().findFirst().map(entry -> {
            final var key = entry.getKey();
            final var startIdx = key.indexOf('(') + 1;
            final var endIdx = key.indexOf(')');
            return key.substring(startIdx, endIdx);
          }).orElse("EUR");
          var amountIn = amounts.stream().filter(entry -> entry.getKey().startsWith("Paid In")).map(Map.Entry::getValue).findFirst().orElse("0");
          var amountOut = amounts.stream().filter(entry -> entry.getKey().startsWith("Paid Out")).map(Map.Entry::getValue).findFirst().orElse("0");
          return RevolutTransaction.parse(date, amountIn, amountOut, description, category, currency);
        })
        .toList();
  }

}
