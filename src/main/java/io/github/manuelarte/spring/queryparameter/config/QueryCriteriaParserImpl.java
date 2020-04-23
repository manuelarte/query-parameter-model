package io.github.manuelarte.spring.queryparameter.config;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.github.manuelarte.spring.queryparameter.exceptions.QueryParserException;
import io.github.manuelarte.spring.queryparameter.model.QueryCriteriaParserContext;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.query.BooleanOperator;
import io.github.manuelarte.spring.queryparameter.query.OtherCriteria;
import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import io.github.manuelarte.spring.queryparameter.query.QueryCriterion;
import java.util.List;

@lombok.RequiredArgsConstructor
public class QueryCriteriaParserImpl implements QueryCriteriaParser {

  private static final char OPERATOR_START = ':';

  private final List<Operator<?>> operators;

  @Override
  public QueryCriteria parse(final String queryCriteria, final QueryCriteriaParserContext context) {
    Preconditions.checkArgument(queryCriteria != null,
        "Can't parse a null query criteria");
    String key;
    Operator<?> operator;
    String value;
    final int operatorStart = queryCriteria.indexOf(OPERATOR_START);
    if (operatorStart > 0) {
      key = queryCriteria.substring(0, operatorStart);
      final String afterKey = queryCriteria.substring(operatorStart);
      operator = operators.stream().filter(o -> o.applies(afterKey)).findFirst()
          .orElseThrow(() -> new QueryParserException("None of the operator matches"));
      value = operator.getValue(afterKey);

      final int otherIndex = queryCriteria.indexOf(value, operatorStart) + value.length();
      if (context != null && !context.allowedKey(key)) {
        throw new QueryParserException("Query parameter key not allowed: " + key);
        // return parse(queryCriteria.substring(otherIndex+1), context);
      } else {
        final QueryCriterion<?> queryCriterion = new QueryCriterion(key, operator,
            operator.formatValue(value));
        return new QueryCriteria(queryCriterion,
            parseOther(queryCriteria.substring(otherIndex), context));
      }
    } else {
      throw new QueryParserException("Can't find the operators in " + queryCriteria);
    }
  }

  private OtherCriteria parseOther(final String subString,
      final QueryCriteriaParserContext context) {
    if (Strings.isNullOrEmpty(subString)) {
      return null;
    } else {
      final BooleanOperator booleanOperator = BooleanOperator.from(subString.charAt(0))
          .orElseThrow(() -> new QueryParserException("Boolean separator not found"));
      final String queryCriteriaLeft = subString.substring(1);
      return OtherCriteria.builder()
          .operator(booleanOperator)
          .criteria(parse(queryCriteriaLeft, context))
          .build();
    }
  }

}
