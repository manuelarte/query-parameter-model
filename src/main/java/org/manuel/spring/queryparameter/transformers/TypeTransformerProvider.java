package org.manuel.spring.queryparameter.transformers;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TypeTransformerProvider {

  private final Map<TransformerKey<?>, TypeTransformer> map;
  private final TypeTransformer defaultTransformer;

  public TypeTransformerProvider(final TypeTransformer defaultTransformer) {
    this.map = new ConcurrentHashMap<>();
    this.defaultTransformer = defaultTransformer;
  }

  public <V> void addTransformer(final Class<V> entityClazz, final String criterionKey,
      final TypeTransformer typeTransformer) {
    this.map.put(new TransformerKey<>(entityClazz, criterionKey), typeTransformer);
  }

  public TypeTransformer getTransformer(final Class<?> entity, final String criterionKey) {
    final TransformerKey<?> transformerKey = new TransformerKey<>(entity, criterionKey);
    return this.map.containsKey(transformerKey) ? this.map.get(transformerKey) : defaultTransformer;
  }

  private static class TransformerKey<V> {

    private final Class<V> entityClass;
    private final String criterionKey;

    public TransformerKey(final Class<V> entityClass, final String criterionKey) {
      this.entityClass = entityClass;
      this.criterionKey = criterionKey;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      TransformerKey<?> that = (TransformerKey<?>) o;
      return Objects.equals(entityClass, that.entityClass) &&
          Objects.equals(criterionKey, that.criterionKey);
    }

    @Override
    public int hashCode() {
      return Objects.hash(entityClass, criterionKey);
    }
  }

}
