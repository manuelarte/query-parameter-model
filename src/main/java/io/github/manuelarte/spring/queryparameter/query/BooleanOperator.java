package io.github.manuelarte.spring.queryparameter.query;

import java.util.Arrays;
import java.util.Optional;

public enum BooleanOperator {
  AND(';'), OR('|');

  @lombok.Getter
  private final char separator;

  BooleanOperator(final char separator) {
    this.separator = separator;
  }

  public static Optional<BooleanOperator> from(char separator) {
    return Arrays.stream(values()).filter(it -> it.separator == separator).findFirst();
  }

}
