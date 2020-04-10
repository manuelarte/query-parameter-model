package org.manuel.spring.queryparameter.config;

import org.manuel.spring.queryparameter.query.QueryCriteria;
import org.springframework.core.convert.converter.Converter;

public class StringQueryCriteriaConverter implements Converter<String, QueryCriteria> {

  private final QueryCriteriaParser queryCriteriaParser;

  public StringQueryCriteriaConverter(final QueryCriteriaParser queryCriteriaParser) {
    this.queryCriteriaParser = queryCriteriaParser;
  }

  @Override
  public QueryCriteria convert(String source) {
    return queryCriteriaParser.parse(source);
  }
}
