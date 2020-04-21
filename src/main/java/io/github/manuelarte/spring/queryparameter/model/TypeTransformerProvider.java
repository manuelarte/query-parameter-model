package io.github.manuelarte.spring.queryparameter.model;

import io.github.manuelarte.spring.queryparameter.transformers.TypeTransformer;

@lombok.RequiredArgsConstructor
public class TypeTransformerProvider {

  private final TypeTransformerRegistry registry;
  private final TypeTransformer defaultTransformer;

  @SuppressWarnings("unused")
  public TypeTransformer getTransformer(final Class<?> entity, final String criterionKey) {
    final TypeTransformerRegistry.TransformerKey<?> transformerKey =
        new TypeTransformerRegistry.TransformerKey<>(entity, criterionKey);
    return this.registry.containsKey(transformerKey) ? this.registry.get(transformerKey)
        : defaultTransformer;
  }

}
