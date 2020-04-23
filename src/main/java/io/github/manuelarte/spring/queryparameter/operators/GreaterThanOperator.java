package io.github.manuelarte.spring.queryparameter.operators;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(GreaterThanOperator.ORDER)
@lombok.EqualsAndHashCode
public class GreaterThanOperator extends AbstractMiddleOperator {

  public static final int ORDER = GreaterThanOrEqualsOperator.ORDER + 10;

  private static final String OPERATOR = ":>";

  @Override
  String getOperator() {
    return OPERATOR;
  }

}
