package io.github.manuelarte.spring.queryparameter.operators;

import java.util.List;

/**
 * In Operator, key:!in:(value,value2,value3).
 */
@lombok.RequiredArgsConstructor
@lombok.EqualsAndHashCode
public class NotInOperator implements Operator<List<String>> {

  private static final String OPERATOR_PREFIX = ":!";

  private final InOperator inOperator;

  /**
   * Expecting (xxx,xxx,xxx,...)
   *
   * @param value The value in string format.
   * @return The list of possible values.
   */
  @Override
  public List<String> formatValue(final String value) {
    return inOperator.formatValue(value);
  }

  @Override
  public String getValue(final String afterKey) {
    return inOperator.getValue(":" + afterKey.substring(OPERATOR_PREFIX.length()));
  }

  @Override
  public boolean applies(final String afterKey) {
    return afterKey.startsWith(OPERATOR_PREFIX)
        && inOperator.applies(":" + afterKey.substring(OPERATOR_PREFIX.length()));
  }

}
