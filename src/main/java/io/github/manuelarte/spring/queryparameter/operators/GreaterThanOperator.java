package io.github.manuelarte.spring.queryparameter.operators;

@lombok.EqualsAndHashCode(callSuper = true)
public class GreaterThanOperator extends AbstractMiddleOperator {

  private static final String OPERATOR = ":>";

  @Override
  String getOperator() {
    return OPERATOR;
  }

}
