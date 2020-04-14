package io.github.manuelarte.spring.queryparameter.query;

import io.github.manuelarte.spring.queryparameter.operators.Operator;
import java.util.Objects;

public final class QueryCriterion<V> {

  private final String key;
  private final Operator<V> operator;
  private final V value;

  public QueryCriterion(final String key, final Operator<V> operator, final V value) {
    this.key = key;
    this.operator = operator;
    this.value = value;
  }

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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final QueryCriterion that = (QueryCriterion) o;
    return Objects.equals(key, that.key)
        && Objects.equals(operator, that.operator)
        && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, operator, value);
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
