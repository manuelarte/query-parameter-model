package io.github.manuelarte.spring.queryparameter.operators;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InOperatorTest {

  private static final Operator<List<String>> IN_OPERATOR = new InOperator();

  @Test
  @DisplayName("Testing simple scenario, one character value")
  public void testSingleEleent() {
    final String afterKey = ":in:(3)";
    final List<String> actual = IN_OPERATOR.formatValue(IN_OPERATOR.getValue(afterKey));
    assertThat(actual, containsInRelativeOrder("3"));
  }

  @Test
  @DisplayName("Testing simple scenario, one word value")
  public void testWordInputSuccess() {
    final String afterKey = ":in:(value)";
    final List<String> actual = IN_OPERATOR.formatValue(IN_OPERATOR.getValue(afterKey));
    assertThat(actual, containsInRelativeOrder("value"));
  }

  @Test
  @DisplayName("Testing not well-formed equal operator")
  public void testWithParenthesisValue() {
    final String afterKey = ":(value)";
    assertThrows(IllegalArgumentException.class,
        () -> IN_OPERATOR.getValue(IN_OPERATOR.getValue(afterKey)));
  }

  @Test
  @DisplayName("Testing simple scenario, one word value")
  public void testSeveralWordsInputSuccess() {
    final String afterKey = ":in:(value,value2)";
    final List<String> actual = IN_OPERATOR.formatValue(IN_OPERATOR.getValue(afterKey));
    final List<String> expected = Arrays.asList("value", "value2");
    assertThat(actual, containsInRelativeOrder(expected.toArray(new String[expected.size()])));
  }

}
