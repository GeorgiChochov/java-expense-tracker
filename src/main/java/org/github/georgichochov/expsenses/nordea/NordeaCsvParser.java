package org.github.georgichochov.expsenses.nordea;

import de.siegmar.fastcsv.reader.NamedCsvReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.github.georgichochov.expsenses.ExpenseTransaction;

import java.util.List;

public class NordeaCsvParser {

  private static final Logger LOG = LogManager.getLogger(NordeaCsvParser.class);

  protected static final char CSV_SEPARATOR = ';';

  private final NordeaInputSanitizer inputSanitizer;

  public NordeaCsvParser(final NordeaInputSanitizer inputSanitizer) {
    this.inputSanitizer = inputSanitizer;
  }

  public List<? extends ExpenseTransaction> parseTransactions(final String input) {
    return parse(inputSanitizer.sanitize(input));
  }

  private List<? extends ExpenseTransaction> parse(final String input) {
    return NamedCsvReader.builder()
        .fieldSeparator(CSV_SEPARATOR)
        .build(input)
        .stream().map(row -> {
          var date = row.getField("Bokföringsdag");
          var amount = row.getField("Belopp");
          var description = row.getField("Rubrik");
          var currency = row.getField("Valuta");
          return NordeaTransaction.parse(date, amount, description, currency);
        })
        .toList();
  }

}
