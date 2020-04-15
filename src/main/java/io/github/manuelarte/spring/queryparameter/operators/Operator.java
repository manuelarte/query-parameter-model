package io.github.manuelarte.spring.queryparameter.operators;

/**
 * Operator interface.
 *
 * @param <V> the expected value, it can be a string, or a collection, range, etc.
 */
public interface Operator<V> {

  /**
   * Return the value already mapped.
   *
   * @param value The value in string format
   * @return The value formatted
   */
  V formatValue(String value);

  String getValue(String afterKey);

  boolean applies(String afterKey);

}
