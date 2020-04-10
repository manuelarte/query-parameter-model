package org.manuel.spring.queryparameter.operators;

/**
 * @param <V> the expected value, it can be a string, or a collection, range, etc
 */
public interface Operator<V> {

  /**
   * Return the value already mapped
   *
   * @param value
   * @return
   */
  V formatValue(String value);

  String getValue(String afterKey);

  boolean applies(String queryCriterion);

}
