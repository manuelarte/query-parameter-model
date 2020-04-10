package org.manuel.spring.queryparameter.operators;

/**
 * @param <V> the expected value, it can be a string, or a collection, range, etc
 */
public interface RangeOperator<V> extends Operator<V> {

  /**
   * Returns the value as it comes after the operator
   *
   * @param queryCriterion
   * @return
   */
  String getStringValue(String queryCriterion);

}
