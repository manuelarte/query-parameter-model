package io.github.manuelarte.spring.queryparameter.operators;

/**
 * Not Operator, key:!(other operator), e.g. key:!:value, - key != value
 */
@lombok.RequiredArgsConstructor
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = true)
public class NotAbstractMiddleOperatorOperator extends AbstractMiddleOperator {

  private static final String OPERATOR_PREFIX = ":!";

  private final AbstractMiddleOperator abstractMiddleOperator;

  @Override
  String getOperator() {
    return OPERATOR_PREFIX + abstractMiddleOperator.getOperator().substring(1);
  }

}
