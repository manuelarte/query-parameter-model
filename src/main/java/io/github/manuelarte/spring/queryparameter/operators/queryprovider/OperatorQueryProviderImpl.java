package io.github.manuelarte.spring.queryparameter.operators.queryprovider;

import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.util.TriPredicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class OperatorQueryProviderImpl<T extends OperatorQuery<?, Q>, Q>
    implements OperatorQueryProvider<T, Q> {

  private final List<OperatorQueryEntry> operatorPredicateEntries;

  public OperatorQueryProviderImpl() {
    this.operatorPredicateEntries = Collections.synchronizedList(new ArrayList<>());
  }


  @Override
  public void addOperatorQuerySelector(
      final TriPredicate<Class<?>, String, Operator<Object>> predicate,
      final T operatorPredicate) {
    this.operatorPredicateEntries.add(new OperatorQueryEntry<>(predicate, operatorPredicate));
  }

  @Override
  public T getOperatorQuery(final Class<?> entity,
      final String criterionKey, final Operator<Object> operator) {
    final OperatorQueryEntry operatorQueryEntry = operatorPredicateEntries.stream()
        .filter(o -> o.predicate.test(entity, criterionKey, operator)).findFirst()
        .orElseThrow(() -> new RuntimeException("query param operator provider not found"));
    return (T) operatorQueryEntry.operatorPredicate;
  }

  private static final class OperatorQueryEntry<T extends OperatorQuery<?, Q>, Q> {

    private final TriPredicate<Class<?>, String, Operator<Object>> predicate;
    private final T operatorPredicate;

    public OperatorQueryEntry(final TriPredicate<Class<?>, String, Operator<Object>> predicate,
        T operatorPredicate) {
      this.predicate = predicate;
      this.operatorPredicate = operatorPredicate;
    }
  }
}
