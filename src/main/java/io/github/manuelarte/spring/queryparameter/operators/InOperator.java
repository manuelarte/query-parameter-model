package io.github.manuelarte.spring.queryparameter.operators;

import com.google.common.base.Preconditions;
import io.github.manuelarte.spring.queryparameter.query.BooleanOperator;
import java.util.Arrays;
import java.util.List;

/**
 * In Operator, key:in:(value,value2,value3).
 */
@lombok.EqualsAndHashCode
public class InOperator implements Operator<List<String>> {

  private static final String OPERATOR = ":in:";

  /**
   * Expecting (xxx,xxx,xxx,...)
   *
   * @param value The value in string format.
   * @return The list of possible values.
   */
  @Override
  public List<String> formatValue(final String value) {
    return Arrays.asList(value.substring(1, value.length() - 1).split(","));
  }

  @Override
  public String getValue(final String afterKey) {
    Preconditions
        .checkArgument(applies(afterKey), "Can't get the value of a criterion that does not apply");
    final String noOperator = afterKey.substring(OPERATOR.length());

    final int separatorIndex = Math.min(
        noOperator.indexOf(BooleanOperator.AND.getSeparator()) == -1
            ? Integer.MAX_VALUE : noOperator.indexOf(BooleanOperator.AND.getSeparator()),
        noOperator.indexOf(BooleanOperator.OR.getSeparator()) == -1
            ? Integer.MAX_VALUE : noOperator.indexOf(BooleanOperator.OR.getSeparator())
    );
    return separatorIndex == Integer.MAX_VALUE ? noOperator
        : afterKey.substring(OPERATOR.length(), separatorIndex + OPERATOR.length());
  }

  @Override
  public boolean applies(final String afterKey) {
    return OPERATOR.equals(afterKey.substring(0, OPERATOR.length()))
        && afterKey.charAt(OPERATOR.length()) == '('
        && afterKey.substring(OPERATOR.length() + 1).contains(")");
  }

}
