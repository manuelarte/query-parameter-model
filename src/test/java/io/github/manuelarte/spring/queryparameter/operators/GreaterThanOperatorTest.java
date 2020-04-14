package io.github.manuelarte.spring.queryparameter.operators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GreaterThanOperatorTest {

  private static final Operator<String> GREATER_THAN_OPERATOR = new GreaterThanOperator();

  @Test
  @DisplayName("Testing simple scenario, one integer value")
  public void testIntegerInput() {
    final String afterKey = ":>3";
    final String obtained = GREATER_THAN_OPERATOR
        .formatValue(GREATER_THAN_OPERATOR.getValue(afterKey));
    assertEquals("3", obtained);
  }

  @Test
  @DisplayName("Testing simple scenario, one double value")
  public void testDoubleInput() {
    final String afterKey = ":>31.1";
    final String obtained = GREATER_THAN_OPERATOR
        .formatValue(GREATER_THAN_OPERATOR.getValue(afterKey));
    assertEquals("31.1", obtained);
  }

  @Test
  @DisplayName("Testing simple scenario, one word value")
  public void testStringInput() {
    final String afterKey = ":>value";
    final String obtained = GREATER_THAN_OPERATOR
        .formatValue(GREATER_THAN_OPERATOR.getValue(afterKey));
    assertEquals("value", obtained);
  }

  @Test
  @DisplayName("Testing simple scenario, not well-formed greaterThan operator")
  public void testWithParenthesis() {
    final String afterKey = ":(3)";
    assertThrows(IllegalArgumentException.class,
        () -> GREATER_THAN_OPERATOR.formatValue(GREATER_THAN_OPERATOR.getValue(afterKey)));
  }

  @Test
  @DisplayName("Testing not well-formed greaterThan operator")
  public void withGreaterThanEqualToTest() {
    final String afterKey = ">=3";
    assertThrows(IllegalArgumentException.class,
        () -> GREATER_THAN_OPERATOR.formatValue(GREATER_THAN_OPERATOR.getValue(afterKey)));
  }

}
