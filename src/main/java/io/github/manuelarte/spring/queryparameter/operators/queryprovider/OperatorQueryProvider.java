package io.github.manuelarte.spring.queryparameter.operators.queryprovider;

import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.util.TriPredicate;

@SuppressWarnings("unused")
public interface OperatorQueryProvider<T extends OperatorQuery<?, Q>, Q> {

  void addOperatorQuerySelector(TriPredicate<Class<?>, String, Operator<Object>> predicate,
      T operatorCriteria);

  T getOperatorQuery(Class<?> entity, String criterionKey,
      Operator<Object> operator);

}
