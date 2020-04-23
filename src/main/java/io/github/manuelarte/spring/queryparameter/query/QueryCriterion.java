package io.github.manuelarte.spring.queryparameter.query;

import io.github.manuelarte.spring.queryparameter.operators.Operator;

@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode
public final class QueryCriterion<V> {

  private final String key;
  private final Operator<V> operator;
  private final V value;

  public static <V> QueryCriterionBuilder<V> builder() {
    return new QueryCriterionBuilder<>();
  }

  public String getKey() {
    return key;
  }

  public Operator<V> getOperator() {
    return operator;
  }

  public V getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.format("key:%s, operator:%s, value:%s", key, operator, value);
  }

  public static final class QueryCriterionBuilder<V> {

    private String key;
    private Operator<V> operator;
    private V value;

    @SuppressWarnings("unused")
    public QueryCriterionBuilder key(final String key) {
      this.key = key;
      return this;
    }

    public QueryCriterionBuilder operator(final Operator<V> operator) {
      this.operator = operator;
      return this;
    }

    public QueryCriterionBuilder value(final V value) {
      this.value = value;
      return this;
    }

    @SuppressWarnings("unused")
    public QueryCriterion build() {
      return new QueryCriterion<>(key, operator, value);
    }

  }

}
