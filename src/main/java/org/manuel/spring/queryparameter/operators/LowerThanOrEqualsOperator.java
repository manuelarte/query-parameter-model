package org.manuel.spring.queryparameter.operators;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(LowerThanOrEqualsOperator.ORDER)
public class LowerThanOrEqualsOperator extends AbstractMiddleOperator {

  public static final int ORDER = EqualsOperator.ORDER + 10;

  private static final String OPERATOR = ":<=";

  @Override
  String getOperator() {
    return OPERATOR;
  }

}
