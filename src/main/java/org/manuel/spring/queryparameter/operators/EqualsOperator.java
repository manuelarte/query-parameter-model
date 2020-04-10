package org.manuel.spring.queryparameter.operators;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(EqualsOperator.ORDER)
public class EqualsOperator extends AbstractMiddleOperator {

  public static final int ORDER = 0;

  private static final String OPERATOR = "::";

  @Override
  String getOperator() {
    return OPERATOR;
  }

  @Override
  public boolean equals(final Object o) {
    return this == o || o instanceof EqualsOperator;
  }

  @Override
  public int hashCode() {
    return EqualsOperator.class.hashCode();
  }
}
