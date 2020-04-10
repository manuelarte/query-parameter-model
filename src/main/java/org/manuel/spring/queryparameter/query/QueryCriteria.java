package org.manuel.spring.queryparameter.query;

import com.google.common.base.Preconditions;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class QueryCriteria {

  private final QueryCriterion<?> criterion;
  private final OtherCriteria other;

  public QueryCriteria(final QueryCriterion criterion) {
    this(criterion, null);
  }

  public QueryCriteria(@Nonnull final QueryCriterion criterion,
      @Nullable final OtherCriteria other) {
    Preconditions.checkArgument(criterion != null, "Criterion can't be null");
    this.criterion = criterion;
    this.other = other;
  }

  public static QueryCriteriaBuilder builder() {
    return new QueryCriteriaBuilder();
  }

  public QueryCriterion getCriterion() {
    return this.criterion;
  }

  public Optional<OtherCriteria> getOther() {
    return Optional.ofNullable(other);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryCriteria that = (QueryCriteria) o;
    return Objects.equals(criterion, that.criterion) &&
        Objects.equals(other, that.other);
  }

  @Override
  public int hashCode() {
    return Objects.hash(criterion, other);
  }

  public static class QueryCriteriaBuilder {

    private QueryCriterion criterion;
    private OtherCriteria other;

    public QueryCriteriaBuilder criterion(final QueryCriterion criterion) {
      this.criterion = criterion;
      return this;
    }

    public QueryCriteriaBuilder other(final OtherCriteria other) {
      this.other = other;
      return this;
    }

    /**
     * Syntactic sugar for:
     *
     * <pre> {@code
     * .other(OtherCriteria.builder()
     *     .operator(BooleanOperator.AND)
     *     .criteria(criteria)
     *     .build());
     * }</pre>
     *
     * @param criteria
     * @return
     */
    public QueryCriteriaBuilder and(final QueryCriteria criteria) {
      return this.other(OtherCriteria.builder()
          .operator(BooleanOperator.AND)
          .criteria(criteria)
          .build());
    }

    /**
     * Syntactic sugar for:
     *
     * <pre> {@code
     * .other(OtherCriteria.builder()
     *     .operator(BooleanOperator.OR)
     *     .criteria(criteria)
     *     .build());
     * }</pre>
     *
     * @param criteria
     * @return
     */
    public QueryCriteriaBuilder or(final QueryCriteria criteria) {
      return this.other(OtherCriteria.builder()
          .operator(BooleanOperator.OR)
          .criteria(criteria)
          .build());
    }

    public QueryCriteria build() {
      return new QueryCriteria(criterion, other);
    }
  }
}
