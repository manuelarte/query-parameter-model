package io.github.manuelarte.spring.queryparameter.query;

/**
 * <p>
 * Represents an optional second {@link QueryCriteria} in a QueryCriteria chain.
 * </p>
 * <p>
 * For example, a QueryCriteria could be represented as "criteria := expression [AND criteria]". In
 * this case the "[AND criteria]" part would be the {@link OtherCriteria}
 * </p>
 */
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class OtherCriteria {

  private final BooleanOperator operator;
  private final QueryCriteria criteria;

}
