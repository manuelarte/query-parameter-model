package io.github.manuelarte.spring.queryparameter.config;

import io.github.manuelarte.spring.queryparameter.model.QueryCriteriaParserContext;
import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface QueryCriteriaParser {

  QueryCriteria parse(@Nonnull String queryCriteria, @Nullable QueryCriteriaParserContext context);

  default QueryCriteria parse(@Nonnull String queryCriteria) {
    return parse(queryCriteria, null);
  }

}
