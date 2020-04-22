package io.github.manuelarte.spring.queryparameter.query;

import com.google.common.base.Preconditions;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.val;

@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder(toBuilder = true)
public final class QueryCriteria implements Iterable<QueryCriterion<Object>> {

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

  @Override
  public Iterator<QueryCriterion<Object>> iterator() {
    return new QueryCriteriaIterator(this);
  }

  @Override
  public void forEach(Consumer<? super QueryCriterion<Object>> action) {
    new QueryCriteriaIterator(this).forEachRemaining(action);
  }

  public static class QueryCriteriaIterator implements Iterator<QueryCriterion<Object>> {

    private final QueryCriteria queryCriteria;
    private int position = 0;

    public QueryCriteriaIterator(final QueryCriteria queryCriteria) {
      this.queryCriteria = queryCriteria;
    }

    @Override
    public boolean hasNext() {
      return getNext(position).isPresent();
    }

    @Override
    public QueryCriterion<Object> next() {
      val toReturn = getNext(position).get();
      position++;
      return toReturn;
    }

    private Optional<QueryCriterion<Object>> getNext(final int index) {
      int accIndex = 0;
      if (index == 0) {
        return Optional.of(queryCriteria.getCriterion());
      } else {
        QueryCriteria acc = this.queryCriteria;
        Optional<OtherCriteria> otherCriteria = Optional.empty();
        while (accIndex < index) {
          if (acc.getOther().isPresent()) {
            otherCriteria = acc.getOther();
            acc = otherCriteria.get().getCriteria();
            accIndex++;
          } else {
            return Optional.empty();
          }
        }
        return otherCriteria.map(it -> it.getCriteria().getCriterion());
      }
    }

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
