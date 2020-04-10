package org.manuel.spring.queryparameter.config;

import java.util.List;
import org.manuel.spring.queryparameter.operators.Operator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryCriteriaConfig {

  @Bean
  @ConditionalOnMissingBean
  public QueryCriteriaParser queryCriteriaParser(final List<Operator<?>> operators) {
    return new QueryCriteriaParserImpl(operators);
  }

}
