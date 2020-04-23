package io.github.manuelarte.spring.queryparameter.operators;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(LowerThanOperator.ORDER)
@lombok.EqualsAndHashCode
public class LowerThanOperator extends AbstractMiddleOperator {

  public static final int ORDER = LowerThanOrEqualsOperator.ORDER + 10;

  private static final String OPERATOR = ":<";

  @Override
  String getOperator() {
    return OPERATOR;
  }

}
