package org.manuel.spring.queryparameter.config;

import com.google.common.base.Strings;
import java.util.List;
import org.manuel.spring.queryparameter.exceptions.QueryParserException;
import org.manuel.spring.queryparameter.operators.Operator;
import org.manuel.spring.queryparameter.query.BooleanOperator;
import org.manuel.spring.queryparameter.query.OtherCriteria;
import org.manuel.spring.queryparameter.query.QueryCriteria;
import org.manuel.spring.queryparameter.query.QueryCriterion;

public class QueryCriteriaParserImpl implements QueryCriteriaParser {

  private static final char OPERATOR_START = ':';

  private final List<Operator<?>> operators;

  public QueryCriteriaParserImpl(final List<Operator<?>> operators) {
    this.operators = operators;
  }

  @Override
  public QueryCriteria parse(final String queryCriteria) {
    String key = "";
    Operator<?> operator;
    String value = "";
    final int operatorStart = queryCriteria.indexOf(OPERATOR_START);
    if (operatorStart > 0) {
      key = queryCriteria.substring(0, operatorStart);
      final String afterKey = queryCriteria.substring(operatorStart);
      operator = operators.stream().filter(o -> o.applies(afterKey)).findFirst()
          .orElseThrow(() -> new QueryParserException("None of the operator matches"));
      value = operator.getValue(afterKey);
      final QueryCriterion<?> queryCriterion = new QueryCriterion(key, operator, value);

      final int otherIndex = queryCriteria.indexOf(value, operatorStart) + value.length();
      return new QueryCriteria(queryCriterion, parseOther(queryCriteria.substring(otherIndex)));
    } else {
      throw new QueryParserException("Can't find the operators in " + queryCriteria);
    }
  }

  private OtherCriteria parseOther(final String subString) {
    if (Strings.isNullOrEmpty(subString)) {
      return null;
    } else {
      final BooleanOperator booleanOperator = BooleanOperator.from(subString.charAt(0))
          .orElseThrow(() -> new QueryParserException("Boolean separator not found"));
      final String queryCriteriaLeft = subString.substring(1);
      return OtherCriteria.builder()
          .operator(booleanOperator)
          .criteria(parse(queryCriteriaLeft))
          .build();
    }
  }

}
