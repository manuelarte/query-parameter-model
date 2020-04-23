package io.github.manuelarte.spring.queryparameter.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.collect.Sets;
import io.github.manuelarte.spring.queryparameter.exceptions.QueryParserException;
import io.github.manuelarte.spring.queryparameter.model.QueryCriteriaParserContext;
import io.github.manuelarte.spring.queryparameter.operators.EqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.InOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.NotAbstractMiddleOperatorOperator;
import io.github.manuelarte.spring.queryparameter.operators.NotInOperator;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.query.BooleanOperator;
import io.github.manuelarte.spring.queryparameter.query.OtherCriteria;
import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import io.github.manuelarte.spring.queryparameter.query.QueryCriterion;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * TODO, move to IT because operators order is important.
 */
public class QueryCriteriaParserImplTest {

  private static final List<Operator<?>> OPERATORS = new OperatorConfig().operators();

  private static Stream<Arguments> parseCases() {
    return Stream.of(
        Arguments.of("key::value",
            new QueryCriteria(new QueryCriterion<>("key", new EqualsOperator(), "value"))),
        Arguments.of("key1::value1;key2::value2",
            new QueryCriteria(new QueryCriterion<>("key1", new EqualsOperator(), "value1"),
                new OtherCriteria(BooleanOperator.AND,
                    new QueryCriteria(
                        new QueryCriterion<>("key2", new EqualsOperator(), "value2"))))),
        Arguments.of("key1::value1|key2::value2",
            new QueryCriteria(new QueryCriterion<>("key1", new EqualsOperator(), "value1"),
                new OtherCriteria(BooleanOperator.OR,
                    new QueryCriteria(
                        new QueryCriterion<>("key2", new EqualsOperator(), "value2"))))),
        Arguments.of("key1:<value1;key2:>value2",
            new QueryCriteria(new QueryCriterion<>("key1", new LowerThanOperator(), "value1"),
                new OtherCriteria(BooleanOperator.AND,
                    new QueryCriteria(
                        new QueryCriterion<>("key2", new GreaterThanOperator(), "value2")))))
    );
  }

  @Test
  void testEquals() {
    final String q = "key::value";
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(OPERATORS);
    final QueryCriteria parsed = parser.parse(q);
    final QueryCriteria expected = new QueryCriteria(new QueryCriterion<>("key",
        new EqualsOperator(), "value"));
    assertEquals(expected, parsed);
  }

  @Test
  void testNotEquals() {
    final String q = "key:!:value";
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(OPERATORS);
    final QueryCriteria parsed = parser.parse(q);
    final QueryCriteria expected = new QueryCriteria(new QueryCriterion<>("key",
        new NotAbstractMiddleOperatorOperator(new EqualsOperator()), "value"));
    assertEquals(expected, parsed);
  }

  @Test
  void testNotGreaterThan() {
    final String q = "key:!>value";
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(OPERATORS);
    final QueryCriteria parsed = parser.parse(q);
    final QueryCriteria expected = new QueryCriteria(new QueryCriterion<>("key",
        new NotAbstractMiddleOperatorOperator(new GreaterThanOperator()), "value"));
    assertEquals(expected, parsed);
  }

  @Test
  void testNotGreaterOrEqualThan() {
    final String q = "key:!>=value";
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(OPERATORS);
    final QueryCriteria parsed = parser.parse(q);
    final QueryCriteria expected = new QueryCriteria(new QueryCriterion<>("key",
        new NotAbstractMiddleOperatorOperator(new GreaterThanOrEqualsOperator()), "value"));
    assertEquals(expected, parsed);
  }

  @ParameterizedTest
  @MethodSource("parseCases")
  void parseCasesTest(final String q, final QueryCriteria expected) {
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(OPERATORS);
    final QueryCriteria parsed = parser.parse(q);
    assertEquals(expected, parsed);
  }

  @Test
  void testInstantAndMore() {
    final String q = "startDate:<=2020-04-12T21:16:01.870+02:00"
        + ";startDate:>2020-02-12T21:16:01.871+01:00;sport::FOOTBALL|sport::FUTSAL";
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(OPERATORS);
    final QueryCriteria parsed = parser.parse(q);
    final QueryCriteria expected = QueryCriteria.builder()
        .criterion(new QueryCriterion<>("startDate", new LowerThanOrEqualsOperator(),
            "2020-04-12T21:16:01.870+02:00"))
        .and(new QueryCriterion<>("startDate", new GreaterThanOperator(),
            "2020-02-12T21:16:01.871+01:00"))
        .and(new QueryCriterion<>("sport", new EqualsOperator(), "FOOTBALL"))
        .or(new QueryCriterion<>("sport", new EqualsOperator(), "FUTSAL"))
        .build();
    assertEquals(expected, parsed);
  }

  @Test
  void testInOperator() {
    final String q = "sport:in:(FOOTBALL,FUTSAL)";
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(OPERATORS);
    final QueryCriteria parsed = parser.parse(q);
    final QueryCriteria expected = QueryCriteria.builder()
        .criterion(
            new QueryCriterion<>("sport", new InOperator(), Arrays.asList("FOOTBALL", "FUTSAL")))
        .build();
    assertEquals(expected, parsed);
  }

  @Test
  void testNotIn() {
    final String q = "key:!in:(value1,value2)";
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(OPERATORS);
    final QueryCriteria actual = parser.parse(q);
    final QueryCriteria expected = new QueryCriteria(new QueryCriterion<>("key",
        new NotInOperator(new InOperator()), Arrays.asList("value1", "value2")));
    assertEquals(expected, actual);
  }

  @Test
  void testInstantAndIn() {
    final String q =
        "startDate:<=2020-04-12T21:16:01.870+02:00;startDate:>2020-02-12T21:16:01.871+01:00"
            + ";sport:in:(FOOTBALL,FUTSAL)";
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(OPERATORS);
    final QueryCriteria parsed = parser.parse(q);
    final QueryCriteria expected = QueryCriteria.builder()
        .criterion(new QueryCriterion<>("startDate", new LowerThanOrEqualsOperator(),
            "2020-04-12T21:16:01.870+02:00"))
        .and(new QueryCriterion<>("startDate", new GreaterThanOperator(),
            "2020-02-12T21:16:01.871+01:00"))
        .and(new QueryCriterion<>("sport", new InOperator(), Arrays.asList("FOOTBALL", "FUTSAL")))
        .build();
    assertEquals(expected, parsed);
  }

  @Test
  void testStartDateAndInButStartDateNotAllowed() {
    final String q =
        "startDate:<=2020-04-12T21:16:01.870+02:00;startDate:>2020-02-12T21:16:01.871+01:00"
            + ";sport:in:(FOOTBALL,FUTSAL)";
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(OPERATORS);
    final QueryCriteriaParserContext context = new QueryCriteriaParserContext(
        null, Sets.newHashSet("startDate"));
    assertThrows(QueryParserException.class, () -> parser.parse(q, context));
  }

}
