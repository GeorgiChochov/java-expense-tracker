package org.github.georgichochov.expenses.revolut;

import org.github.georgichochov.expsenses.revolut.RevolutCsvParser;
import org.github.georgichochov.expsenses.revolut.RevolutInputSanitizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.fail;

public class RevolutCsvParserSuite {

  private static final String REVOLUT_STATEMENT_CSV = "Revolut_statement.csv";
  private final RevolutCsvParser parser = new RevolutCsvParser(new RevolutInputSanitizer());

  @Test
  @DisplayName("RevolutCsvParser should parse CSV text correctly")
  void shouldParseCsvCorrectly() {
    try {
      final var resource = this.getClass().getResource("/" + REVOLUT_STATEMENT_CSV);
      if (resource == null) {
        fail("Couldn't find fle: " + REVOLUT_STATEMENT_CSV);
      }
      final var input = Files.readString(Path.of(resource.getPath()), StandardCharsets.UTF_8);
      final var transactions = parser.parseTransactions(input);
      transactions.forEach(System.out::println);
    } catch (IOException e) {
      fail("Couldn't find fle: " + REVOLUT_STATEMENT_CSV, e);
    }
  }

}
