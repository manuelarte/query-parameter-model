package io.github.manuelarte.spring.queryparameter.operators;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(GreaterThanOrEqualsOperator.ORDER)
@lombok.EqualsAndHashCode
public class GreaterThanOrEqualsOperator extends AbstractMiddleOperator {

  public static final int ORDER = LowerThanOperator.ORDER + 10;

  private static final String OPERATOR = ":>=";

  @Override
  String getOperator() {
    return OPERATOR;
  }

}
