package org.manuel.spring.queryparameter.operators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GreaterThanOrEqualToOperatorTest {

  private static final Operator<String> GREATER_THAN_OR_EQUALS_OPERATOR = new GreaterThanOrEqualsOperator();

  @Test
  @DisplayName("Testing simple scenario, one integer value")
  public void greaterThanOrEqualTo_IntegerInput_Success() {
    final String afterKey = ":>=3";
    final String obtained = GREATER_THAN_OR_EQUALS_OPERATOR.getValue(afterKey);
    assertEquals("3", obtained);
  }

  @Test
  @DisplayName("Testing simple scenario, one double value")
  public void greaterThanOrEqualTo_DoubleInput_Success() {
    final String afterKey = ":>=31.1";
    final String obtained = GREATER_THAN_OR_EQUALS_OPERATOR.getValue(afterKey);
    assertEquals("31.1", obtained);
  }

  @Test
  @DisplayName("Testing simple scenario, one word value")
  public void testStringValue() {
    final String afterKey = ":>=value";
    final String obtained = GREATER_THAN_OR_EQUALS_OPERATOR.getValue(afterKey);
    assertEquals("value", obtained);
  }

  @Test
  @DisplayName("Testing not well-formed greaterThanOrEqualTo operator by putting parenthesis")
  public void testValueWithParenthesis() {
    final String afterKey = ":(3)";
    assertThrows(IllegalArgumentException.class,
        () -> GREATER_THAN_OR_EQUALS_OPERATOR.getValue(afterKey));
  }

  @Test
  @DisplayName("Testing not well-formed greaterThanOrEqualTo operator, no equal character")
  public void testWithGreaterThan() {
    final String afterKey = ":>3";
    assertThrows(IllegalArgumentException.class,
        () -> GREATER_THAN_OR_EQUALS_OPERATOR.getValue(afterKey));
  }

}
