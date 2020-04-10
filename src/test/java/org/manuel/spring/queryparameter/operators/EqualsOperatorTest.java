package org.manuel.spring.queryparameter.operators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EqualsOperatorTest {

  private static final Operator<String> EQUALS_OPERATOR = new EqualsOperator();

  @Test
  @DisplayName("Testing simple scenario, one character value")
  public void testNumberInputSuccess() {
    final String afterKey = "::3";
    final String obtained = EQUALS_OPERATOR.getValue(afterKey);
    assertEquals(obtained, "3");
  }

  @Test
  @DisplayName("Testing simple scenario, one word value")
  public void testWordInputSuccess() {
    final String afterKey = "::value";
    final String obtained = EQUALS_OPERATOR.getValue(afterKey);
    assertEquals(obtained, "value");
  }

  @Test
  @DisplayName("Testing not well-formed equal operator")
  public void testWithParenthesisValue() {
    final String afterKey = ":(value)";
    assertThrows(IllegalArgumentException.class, () -> EQUALS_OPERATOR.getValue(afterKey));
  }

}
