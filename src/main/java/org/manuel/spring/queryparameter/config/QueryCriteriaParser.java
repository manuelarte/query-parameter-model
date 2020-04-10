package org.manuel.spring.queryparameter.config;

import org.manuel.spring.queryparameter.query.QueryCriteria;

public interface QueryCriteriaParser {

  QueryCriteria parse(String queryCriteria);

}
