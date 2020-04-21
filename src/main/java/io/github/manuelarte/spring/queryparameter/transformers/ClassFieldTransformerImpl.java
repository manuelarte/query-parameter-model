package io.github.manuelarte.spring.queryparameter.transformers;

import io.github.manuelarte.spring.queryparameter.exceptions.QueryParserException;
import org.springframework.core.convert.ConversionService;

@lombok.AllArgsConstructor
public class ClassFieldTransformerImpl<V> implements TypeTransformer<Object, V> {

  private final ConversionService conversionService;

  @Override
  public V transformValue(final Class<?> entity, final String criterionKey,
      final Object value) {
    try {
      final Class<?> toCast = entity.getDeclaredField(criterionKey).getType();
      if (conversionService.canConvert(String.class, toCast)) {
        return (V) conversionService.convert(value, toCast);
      } else {
        throw new QueryParserException("Can't convert from String to " + toCast.getSimpleName());
      }
    } catch (final NoSuchFieldException e) {
      throw new QueryParserException(e);
    }
  }
}
