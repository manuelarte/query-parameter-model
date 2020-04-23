package io.github.manuelarte.spring.queryparameter.operators;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(EqualsOperator.ORDER)
@lombok.EqualsAndHashCode
public class EqualsOperator extends AbstractMiddleOperator {

  public static final int ORDER = 0;

  private static final String OPERATOR = "::";

  @Override
  String getOperator() {
    return OPERATOR;
  }

}
