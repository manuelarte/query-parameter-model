package org.manuel.spring.queryparameter.query;

import java.util.Objects;

/**
 * <p>
 * Represents an optional second {@link QueryCriteria} in a QueryCriteria chain.
 * </p>
 * <p>
 * For example, a QueryCriteria could be represented as "criteria := expression [AND criteria]". In
 * this case the "[AND criteria]" part would be the {@link OtherCriteria}
 * </p>
 */
public class OtherCriteria {

  private final BooleanOperator operator;
  private final QueryCriteria criteria;

  public OtherCriteria(final BooleanOperator operator, final QueryCriteria queryCriteria) {
    this.operator = operator;
    this.criteria = queryCriteria;
  }

  public static OtherCriteriaBuilder builder() {
    return new OtherCriteriaBuilder();
  }

  public BooleanOperator getOperator() {
    return operator;
  }

  public QueryCriteria getCriteria() {
    return criteria;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OtherCriteria that = (OtherCriteria) o;
    return Objects.equals(operator, that.operator)
        && Objects.equals(criteria, that.criteria);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operator, criteria);
  }

  public static class OtherCriteriaBuilder {

    private BooleanOperator operator;
    private QueryCriteria criteria;

    public OtherCriteriaBuilder operator(final BooleanOperator operator) {
      this.operator = operator;
      return this;
    }

    public OtherCriteriaBuilder criteria(final QueryCriteria criteria) {
      this.criteria = criteria;
      return this;
    }

    public OtherCriteria build() {
      return new OtherCriteria(operator, criteria);
    }

  }
}
