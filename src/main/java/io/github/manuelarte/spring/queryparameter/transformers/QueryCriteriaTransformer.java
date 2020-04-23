package io.github.manuelarte.spring.queryparameter.transformers;

import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import java.util.function.BiFunction;

/**
 * Transformer of the query criteria to be used by the method parameter handler.
 * @param <O> The output type this transformer.
 */
public interface QueryCriteriaTransformer<O> extends BiFunction<Class<?>, QueryCriteria, O> {

  boolean supports(Class<?> clazz);

}
