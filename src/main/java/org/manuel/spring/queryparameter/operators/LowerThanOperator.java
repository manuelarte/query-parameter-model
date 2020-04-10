package org.manuel.spring.queryparameter.operators;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(LowerThanOperator.ORDER)
public class LowerThanOperator extends AbstractMiddleOperator {

  public static final int ORDER = LowerThanOrEqualsOperator.ORDER + 10;

  private static final String OPERATOR = ":<";

  @Override
  String getOperator() {
    return OPERATOR;
  }

  @Override
  public boolean equals(final Object o) {
    return this == o || o instanceof LowerThanOperator;
  }

  @Override
  public int hashCode() {
    return LowerThanOperator.class.hashCode();
  }

}
