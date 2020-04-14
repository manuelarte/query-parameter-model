package io.github.manuelarte.spring.queryparameter.model;

import io.github.manuelarte.spring.queryparameter.transformers.TypeTransformer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class TypeTransformerRegistry {

  private final Map<TransformerKey<?>, TypeTransformer> map;

  public TypeTransformerRegistry() {
    this.map = new HashMap<>();
  }

  public <V> void addTransformer(final Class<V> entityClazz, final String criterionKey,
      final TypeTransformer typeTransformer) {
    this.map.put(new TransformerKey<>(entityClazz, criterionKey), typeTransformer);
  }

  public boolean containsKey(final TransformerKey key) {
    return this.map.containsKey(key);
  }

  public TypeTransformer get(final TransformerKey key) {
    return this.map.get(key);
  }

  public static class TransformerKey<V> {

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
      return Objects.equals(entityClass, that.entityClass)
          && Objects.equals(criterionKey, that.criterionKey);
    }

    @Override
    public int hashCode() {
      return Objects.hash(entityClass, criterionKey);
    }
  }

}
