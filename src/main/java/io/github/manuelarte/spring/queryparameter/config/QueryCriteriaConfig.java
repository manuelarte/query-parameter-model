package io.github.manuelarte.spring.queryparameter.config;

import io.github.manuelarte.spring.queryparameter.model.TypeTransformerProvider;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerRegistry;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.transformers.ClassFieldTransformerImpl;
import io.github.manuelarte.spring.queryparameter.transformers.TypeTransformer;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

@Configuration
@lombok.RequiredArgsConstructor
public class QueryCriteriaConfig {

  private final List<QueryParameterConfig> queryParameterConfigs;

  @Bean
  @ConditionalOnMissingBean
  public QueryCriteriaParser queryCriteriaParser(final List<Operator<?>> operators) {
    return new QueryCriteriaParserImpl(operators);
  }

  @Bean("defaultTypeTransformer")
  public TypeTransformer defaultTypeTransformer(final ConversionService conversionService) {
    return new ClassFieldTransformerImpl(conversionService);
  }

  @Bean
  @ConditionalOnMissingBean
  public TypeTransformerProvider typeTransformerProvider(
      @Qualifier("defaultTypeTransformer") final TypeTransformer typeTransformer,
      final TypeTransformerRegistry typeTransformerRegistry) {
    queryParameterConfigs.forEach(it -> it.addTypeTransformer(typeTransformerRegistry));
    final TypeTransformerProvider typeTransformerProvider =
        new TypeTransformerProvider(typeTransformerRegistry, typeTransformer);
    return typeTransformerProvider;
  }

}
