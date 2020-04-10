package org.manuel.spring.queryparameter.transformers;

public interface TypeTransformer {

  <V> V transformValue(Class<?> entity, String criterionKey, String value);

}
