package io.github.manuelarte.spring.queryparameter.config;

import io.github.manuelarte.spring.queryparameter.model.TypeTransformerRegistry;

/**
 * Interface to add custom type transformers.
 */
public interface QueryParameterConfig {

  void addTypeTransformer(TypeTransformerRegistry typeTransformerRegistry);

}
