package org.manuel.spring.queryparameter.operators;

import com.google.common.base.Preconditions;
import org.manuel.spring.queryparameter.query.BooleanOperator;

public abstract class AbstractMiddleOperator implements Operator<String> {

  abstract String getOperator();

  @Override
  public String formatValue(final String afterKey) {
    return getValue(afterKey);
  }

  @Override
  public String getValue(final String afterKey) {
    Preconditions
        .checkArgument(applies(afterKey), "Can't get the value of a criterion that does not apply");
    final String noOperator = afterKey.substring(getOperator().length());
    final int separatorIndex = Math.max(noOperator.indexOf(BooleanOperator.AND.getSeparator()),
        noOperator.indexOf(BooleanOperator.OR.getSeparator()));
    return separatorIndex == -1 ? noOperator
        : afterKey.substring(getOperator().length(), separatorIndex + getOperator().length());
  }

  @Override
  public boolean applies(final String afterKey) {
    return getOperator().equals(afterKey.substring(0, getOperator().length()));
  }

}
