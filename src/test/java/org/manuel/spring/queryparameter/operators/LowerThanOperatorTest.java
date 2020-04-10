package org.manuel.spring.queryparameter.operators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LowerThanOperatorTest {

  private static final Operator<String> LOWER_THAN_OPERATOR = new LowerThanOperator();

  @Test
  @DisplayName("Testing simple scenario, one integer value")
  public void integerInputTest() {
    final String afterKey = ":<3";
    final String obtained = LOWER_THAN_OPERATOR.getValue(afterKey);
    assertEquals(obtained, "3");
  }

  @Test
  @DisplayName("Testing double value")
  public void doubleInputTest() {
    final String afterKey = ":<31.1";
    final String obtained = LOWER_THAN_OPERATOR.getValue(afterKey);
    assertEquals("31.1", obtained);
  }

  @Test
  @DisplayName("Testing string input")
  public void stringInputTest() {
    final String afterKey = ":<value";
    final String obtained = LOWER_THAN_OPERATOR.getValue(afterKey);
    assertEquals("value", obtained);
  }

  @Test
  @DisplayName("Testing not well-formed lessThan operator")
  public void withParenthesisTest() {
    final String afterKey = ":(3)";
    assertThrows(IllegalArgumentException.class, () -> LOWER_THAN_OPERATOR.getValue(afterKey));
  }

  @Test
  @DisplayName("Testing simple scenario, not well-formed lessThan operator")
  public void testWithGreaterThanOrEqualTo() {
    final String afterKey = ":>=3";
    assertThrows(IllegalArgumentException.class, () -> LOWER_THAN_OPERATOR.getValue(afterKey));
  }

  @Test
  @DisplayName("Testing not well-formed lessThan operator")
  public void testWithGreaterThan() {
    final String afterKey = ":>3";
    assertThrows(IllegalArgumentException.class, () -> LOWER_THAN_OPERATOR.getValue(afterKey));
  }

}
