package io.github.manuelarte.spring.queryparameter.operators;

import com.google.common.base.Preconditions;
import io.github.manuelarte.spring.queryparameter.query.BooleanOperator;

@lombok.EqualsAndHashCode
public abstract class AbstractMiddleOperator implements Operator<String> {

  abstract String getOperator();

  @Override
  public String formatValue(final String value) {
    return value;
  }

  @Override
  public String getValue(final String afterKey) {
    Preconditions
        .checkArgument(applies(afterKey), "Can't get the value of a criterion that does not apply");
    final String noOperator = afterKey.substring(getOperator().length());

    final int separatorIndex = Math.min(
        noOperator.indexOf(BooleanOperator.AND.getSeparator()) == -1
            ? Integer.MAX_VALUE : noOperator.indexOf(BooleanOperator.AND.getSeparator()),
        noOperator.indexOf(BooleanOperator.OR.getSeparator()) == -1
            ? Integer.MAX_VALUE : noOperator.indexOf(BooleanOperator.OR.getSeparator())
    );
    return separatorIndex == Integer.MAX_VALUE ? noOperator
        : afterKey.substring(getOperator().length(), separatorIndex + getOperator().length());
  }

  @Override
  public boolean applies(final String afterKey) {
    return getOperator().equals(afterKey.substring(0, getOperator().length()));
  }

}
