package io.github.manuelarte.spring.queryparameter.operators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LowerThanOrEqualToOperatorTest {

  private static final Operator<String> LOWER_THAN_OR_EQUALS_OPERATOR =
      new LowerThanOrEqualsOperator();

  @Test
  @DisplayName("Testing simple scenario, one integer value")
  public void lessThanOrEqualTo_IntegerInput_Success() {
    final String afterKey = ":<=3";
    final String obtained = LOWER_THAN_OR_EQUALS_OPERATOR
        .formatValue(LOWER_THAN_OR_EQUALS_OPERATOR.getValue(afterKey));
    assertEquals("3", obtained);
  }

  @Test
  @DisplayName("Testing simple scenario, one double value")
  public void lessThanOrEqualTo_DoubleInput_Success() {
    final String afterKey = ":<=31.1";
    final String obtained = LOWER_THAN_OR_EQUALS_OPERATOR
        .formatValue(LOWER_THAN_OR_EQUALS_OPERATOR.getValue(afterKey));
    assertEquals("31.1", obtained);
  }

  @Test
  @DisplayName("Testing simple scenario, one word value")
  public void lessThanOrEqualTo_NoComparableInput_Exception() {
    final String afterKey = ":<=value";
    final String obtained = LOWER_THAN_OR_EQUALS_OPERATOR
        .formatValue(LOWER_THAN_OR_EQUALS_OPERATOR.getValue(afterKey));
    assertEquals("value", obtained);
  }

  @Test
  @DisplayName("Testing no well formed lessThanOrEqualTo operator")
  public void lessThanOrEqualTo_WithParenthesis_Exception() {
    final String afterKey = ":(3)";
    assertThrows(IllegalArgumentException.class,
        () -> LOWER_THAN_OR_EQUALS_OPERATOR.getValue(afterKey));
  }

  @Test
  @DisplayName("Testing no well formed lessThanOrEqualTo operator")
  public void lessThanOrEqualTo_WithGreaterThanOrEqualTo_Exception() {
    final String afterKey = ">=3";
    assertThrows(IllegalArgumentException.class,
        () -> LOWER_THAN_OR_EQUALS_OPERATOR.getValue(afterKey));
  }

}
