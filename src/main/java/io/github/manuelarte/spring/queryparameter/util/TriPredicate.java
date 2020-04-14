package io.github.manuelarte.spring.queryparameter.util;

@FunctionalInterface
@SuppressWarnings("unused")
public interface TriPredicate<T, U, V> {

  boolean test(T t, U u, V v);

}
