package org.manuel.spring.queryparameter.transformers;

import org.manuel.spring.queryparameter.exceptions.QueryParserException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ClassFieldTransformerImpl implements TypeTransformer {

  private final ConversionService conversionService;

  public ClassFieldTransformerImpl(final ConversionService conversionService) {
    this.conversionService = conversionService;
  }

  @Override
  public <V> V transformValue(final Class<?> entity, final String criterionKey,
      final String value) {
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
