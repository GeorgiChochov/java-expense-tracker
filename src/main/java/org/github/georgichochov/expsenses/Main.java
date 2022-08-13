package org.github.georgichochov.expsenses;

import org.apache.logging.log4j.Logger;
import org.github.georgichochov.expsenses.nordea.NordeaCsvParser;
import org.github.georgichochov.expsenses.nordea.NordeaInputSanitizer;
import org.github.georgichochov.expsenses.revolut.RevolutCsvParser;
import org.github.georgichochov.expsenses.revolut.RevolutInputSanitizer;

import static org.apache.logging.log4j.LogManager.getLogger;

public class Main {
  private static final Logger LOG = getLogger(Main.class);

  public static void main(String[] args) {
    final NordeaCsvParser nordeaCsvParser = new NordeaCsvParser(new NordeaInputSanitizer());
    final RevolutCsvParser revolutCsvParser = new RevolutCsvParser(new RevolutInputSanitizer());
  }

}


