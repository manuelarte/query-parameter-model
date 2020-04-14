package io.github.manuelarte.spring.queryparameter.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.manuelarte.spring.queryparameter.operators.EqualsOperator;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class QueryCriteriaTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.forClass(QueryCriteria.class)
        .withPrefabValues(OtherCriteria.class,
            new OtherCriteria(BooleanOperator.AND,
                QueryCriteria.fromCriterion("key", new EqualsOperator(), "value")),
            new OtherCriteria(BooleanOperator.OR,
                QueryCriteria.fromCriterion("key", new EqualsOperator(), "value")))
        .verify();
  }

  @Test
  public void testAddInBuilder() {
    final QueryCriteria actual = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .and(new QueryCriterion("key2", new EqualsOperator(), "value2")).build();

    final QueryCriteria expected = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .other(new OtherCriteria(BooleanOperator.AND,
            new QueryCriteria(new QueryCriterion("key2",
                new EqualsOperator(), "value2")))).build();
    assertEquals(expected, actual);
  }

  @Test
  public void testTwoAddInBuilder() {
    final QueryCriteria actual = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .and(new QueryCriterion("key2", new EqualsOperator(), "value2"))
        .and(new QueryCriterion("key3", new EqualsOperator(), "value3"))
        .build();

    final QueryCriteria expected = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .other(
            new OtherCriteria(BooleanOperator.AND,
                new QueryCriteria(
                    new QueryCriterion("key2", new EqualsOperator(), "value2"),
                    new OtherCriteria(BooleanOperator.AND, new QueryCriteria(
                        new QueryCriterion("key3", new EqualsOperator(), "value3"))))
            )
        )
        .build();
    assertEquals(expected, actual);
  }

  @Test
  public void testOrInBuilder() {
    final QueryCriteria actual = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .or(new QueryCriterion("key2", new EqualsOperator(), "value2")).build();

    final QueryCriteria expected = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .other(new OtherCriteria(BooleanOperator.OR,
            new QueryCriteria(new QueryCriterion("key2",
                new EqualsOperator(), "value2")))).build();
    assertEquals(expected, actual);
  }

  @Test
  public void testTwoOrInBuilder() {
    final QueryCriteria actual = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .or(new QueryCriterion("key2", new EqualsOperator(), "value2"))
        .or(new QueryCriterion("key3", new EqualsOperator(), "value3"))
        .build();

    final QueryCriteria expected = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .other(
            new OtherCriteria(BooleanOperator.OR,
                new QueryCriteria(
                    new QueryCriterion("key2", new EqualsOperator(), "value2"),
                    new OtherCriteria(BooleanOperator.OR, new QueryCriteria(
                        new QueryCriterion("key3", new EqualsOperator(), "value3"))))
            )
        )
        .build();
    assertEquals(expected, actual);
  }

  @Test
  public void testAndOrInBuilder() {
    final QueryCriteria actual = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .and(new QueryCriterion("key2", new EqualsOperator(), "value2"))
        .or(new QueryCriterion("key3", new EqualsOperator(), "value3"))
        .build();

    final QueryCriteria expected = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .other(
            new OtherCriteria(BooleanOperator.AND,
                new QueryCriteria(
                    new QueryCriterion("key2", new EqualsOperator(), "value2"),
                    new OtherCriteria(BooleanOperator.OR, new QueryCriteria(
                        new QueryCriterion("key3", new EqualsOperator(), "value3"))))
            )
        )
        .build();
    assertEquals(expected, actual);
  }

  @Test
  public void testOrAndInBuilder() {
    final QueryCriteria actual = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .or(new QueryCriterion("key2", new EqualsOperator(), "value2"))
        .and(new QueryCriterion("key3", new EqualsOperator(), "value3"))
        .build();

    final QueryCriteria expected = QueryCriteria.builder()
        .criterion(new QueryCriterion("key", new EqualsOperator(), "value"))
        .other(
            new OtherCriteria(BooleanOperator.OR,
                new QueryCriteria(
                    new QueryCriterion("key2", new EqualsOperator(), "value2"),
                    new OtherCriteria(BooleanOperator.AND, new QueryCriteria(
                        new QueryCriterion("key3", new EqualsOperator(), "value3"))))
            )
        )
        .build();
    assertEquals(expected, actual);
  }

}
