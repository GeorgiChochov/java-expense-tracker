package org.github.georgichochov.expenses.nordea;

import org.github.georgichochov.expsenses.nordea.NordeaCsvParser;
import org.github.georgichochov.expsenses.nordea.NordeaInputSanitizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.fail;

public class NordeaCsvParserSuite {

  private static final String NORDEA_STATEMENT_CSV = "Nodrea_statement.csv";
  private final NordeaCsvParser parser = new NordeaCsvParser(new NordeaInputSanitizer());

  @Test
  @DisplayName("NordeaCsvParser should parse CSV text correctly")
  void shouldParseCsvCorrectly() {
    try {
      final var resource = this.getClass().getResource("/" + NORDEA_STATEMENT_CSV);
      if (resource == null) {
        fail("Couldn't find fle: " + NORDEA_STATEMENT_CSV);
      }
      final var input = Files.readString(Path.of(resource.getPath()), StandardCharsets.UTF_8);
      final var transactions = parser.parseTransactions(input);
      transactions.forEach(System.out::println);
    } catch (IOException e) {
      fail("Couldn't find fle: " + NORDEA_STATEMENT_CSV, e);
    }
  }
}
