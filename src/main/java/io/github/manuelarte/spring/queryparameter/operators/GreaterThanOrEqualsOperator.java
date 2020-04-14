package io.github.manuelarte.spring.queryparameter.operators;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(GreaterThanOrEqualsOperator.ORDER)
public class GreaterThanOrEqualsOperator extends AbstractMiddleOperator {

  public static final int ORDER = LowerThanOperator.ORDER + 10;

  private static final String OPERATOR = ":>=";

  @Override
  String getOperator() {
    return OPERATOR;
  }

  @Override
  public boolean equals(Object o) {
    return this == o || o instanceof GreaterThanOrEqualsOperator;
  }

  @Override
  public int hashCode() {
    return GreaterThanOrEqualsOperator.class.hashCode();
  }

}
