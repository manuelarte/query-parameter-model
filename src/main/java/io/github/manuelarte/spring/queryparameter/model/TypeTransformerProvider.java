package io.github.manuelarte.spring.queryparameter.model;

import io.github.manuelarte.spring.queryparameter.transformers.TypeTransformer;

public class TypeTransformerProvider {

  /**
   * Change this map to a predicate, and then we filter based on the transformer key.
   **/
  private final TypeTransformerRegistry registry;
  private final TypeTransformer defaultTransformer;

  public TypeTransformerProvider(final TypeTransformerRegistry registry,
      final TypeTransformer defaultTransformer) {
    this.registry = registry;
    this.defaultTransformer = defaultTransformer;
  }

  public TypeTransformer getTransformer(final Class<?> entity, final String criterionKey) {
    final TypeTransformerRegistry.TransformerKey<?> transformerKey =
        new TypeTransformerRegistry.TransformerKey<>(entity, criterionKey);
    return this.registry.containsKey(transformerKey) ? this.registry.get(transformerKey)
        : defaultTransformer;
  }

}
