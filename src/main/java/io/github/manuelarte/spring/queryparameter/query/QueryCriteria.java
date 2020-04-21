package io.github.manuelarte.spring.queryparameter.query;

import com.google.common.base.Preconditions;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@lombok.EqualsAndHashCode
@lombok.Builder(toBuilder = true)
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

  public static <V> QueryCriteria fromCriterion(final String key,
      final Operator<V> operator, final String value) {
    return new QueryCriteria(new QueryCriterion<>(key, operator, operator.formatValue(value)));
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

  public static class QueryCriteriaBuilder {

    /**
     * Concatenate 'and' to the next available OtherCriteria.
     *
     * @param criterion The criterion to apply.
     * @return the builder.
     */
    public QueryCriteriaBuilder and(final QueryCriterion criterion) {
      if (this.other == null) {
        return other(new OtherCriteria(BooleanOperator.AND, new QueryCriteria(criterion)));
      } else {
        final QueryCriteria build = this.other.getCriteria().toBuilder().and(criterion).build();
        return other(this.other.toBuilder().criteria(build).build());
      }
    }

    /**
     * Concatenate 'or' to the next available OtherCriteria.
     *
     * @param criterion The criterion to apply.
     * @return the builder.
     */
    public QueryCriteriaBuilder or(final QueryCriterion criterion) {
      if (this.other == null) {
        return other(new OtherCriteria(BooleanOperator.OR, new QueryCriteria(criterion)));
      } else {
        final QueryCriteria build = this.other.getCriteria().toBuilder().or(criterion).build();
        return other(this.other.toBuilder().criteria(build).build());
      }
    }

    @Override
    public String toString() {
      return String.format("criterion:%s, other:%s", criterion, other);
    }
  }
}
