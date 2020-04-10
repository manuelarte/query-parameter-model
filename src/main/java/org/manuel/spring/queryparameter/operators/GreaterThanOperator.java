package org.manuel.spring.queryparameter.operators;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(GreaterThanOperator.ORDER)
public class GreaterThanOperator extends AbstractMiddleOperator {

  public static final int ORDER = GreaterThanOrEqualsOperator.ORDER + 10;

  private static final String OPERATOR = ":>";

  @Override
  String getOperator() {
    return OPERATOR;
  }

  @Override
  public boolean equals(Object o) {
    return this == o || o instanceof GreaterThanOperator;
  }

  @Override
  public int hashCode() {
    return GreaterThanOperator.class.hashCode();
  }

}
