package org.manuel.spring.queryparameter.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.manuel.spring.queryparameter.operators.EqualsOperator;
import org.manuel.spring.queryparameter.operators.GreaterThanOperator;
import org.manuel.spring.queryparameter.operators.GreaterThanOrEqualsOperator;
import org.manuel.spring.queryparameter.operators.LowerThanOperator;
import org.manuel.spring.queryparameter.operators.LowerThanOrEqualsOperator;
import org.manuel.spring.queryparameter.operators.Operator;
import org.manuel.spring.queryparameter.query.BooleanOperator;
import org.manuel.spring.queryparameter.query.OtherCriteria;
import org.manuel.spring.queryparameter.query.QueryCriteria;
import org.manuel.spring.queryparameter.query.QueryCriterion;

public class QueryCriteriaParserImplTest {

  private static Stream<Arguments> parseCases() {
    return Stream.of(
        Arguments.of("key::value",
            new QueryCriteria(new QueryCriterion("key", new EqualsOperator(), "value"))),
        Arguments.of("key1::value1;key2::value2",
            new QueryCriteria(new QueryCriterion("key1", new EqualsOperator(), "value1"),
                new OtherCriteria(BooleanOperator.AND,
                    new QueryCriteria(
                        new QueryCriterion("key2", new EqualsOperator(), "value2"))))),
        Arguments.of("key1::value1|key2::value2",
            new QueryCriteria(new QueryCriterion("key1", new EqualsOperator(), "value1"),
                new OtherCriteria(BooleanOperator.OR,
                    new QueryCriteria(
                        new QueryCriterion("key2", new EqualsOperator(), "value2"))))),
        Arguments.of("key1:<value1;key2:>value2",
            new QueryCriteria(new QueryCriterion("key1", new LowerThanOperator(), "value1"),
                new OtherCriteria(BooleanOperator.AND,
                    new QueryCriteria(
                        new QueryCriterion("key2", new GreaterThanOperator(), "value2")))))
    );
  }

  @Test
  void testEquals() {
    final String q = "key::value";
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(getAllOperators());
    final QueryCriteria parsed = parser.parse(q);
    final QueryCriteria expected = new QueryCriteria(new QueryCriterion("key",
        new EqualsOperator(), "value"));
    assertEquals(expected, parsed);
  }

  @ParameterizedTest
  @MethodSource("parseCases")
  void parseCasesTest(final String q, final QueryCriteria expected) {
    final QueryCriteriaParser parser = new QueryCriteriaParserImpl(getAllOperators());
    final QueryCriteria parsed = parser.parse(q);
    assertEquals(expected, parsed);
  }

  private List<Operator<?>> getAllOperators() {
    return Arrays.asList(new EqualsOperator(), new GreaterThanOperator(),
        new GreaterThanOrEqualsOperator(), new LowerThanOperator(),
        new LowerThanOrEqualsOperator());
  }

}
