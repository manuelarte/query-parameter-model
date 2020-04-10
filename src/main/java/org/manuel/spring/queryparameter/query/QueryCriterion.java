package org.manuel.spring.queryparameter.query;

import java.util.Objects;
import org.manuel.spring.queryparameter.operators.Operator;

public class QueryCriterion<V> {

  private final String key;
  private final Operator<V> operator;
  private final String value;

  public QueryCriterion(final String key, final Operator<V> operator, final String value) {
    this.key = key;
    this.operator = operator;
    this.value = value;
  }

  public static <T> QueryCriterionBuilder<T> builder() {
    return new QueryCriterionBuilder<>();
  }

  public String getKey() {
    return key;
  }

  public Operator<V> getOperator() {
    return operator;
  }

  public String getValue() {
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
    return Objects.equals(key, that.key) &&
        Objects.equals(operator, that.operator) &&
        Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, operator, value);
  }

  public static final class QueryCriterionBuilder<V> {

    private String key;
    private Operator<V> operator;
    private String value;

    public QueryCriterionBuilder key(final String key) {
      this.key = key;
      return this;
    }

    public QueryCriterionBuilder operator(final Operator<V> operator) {
      this.operator = operator;
      return this;
    }

    public QueryCriterionBuilder value(final String value) {
      this.value = value;
      return this;
    }

    public QueryCriterion build() {
      return new QueryCriterion<>(key, operator, value);
    }

  }

}
