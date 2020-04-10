package org.manuel.spring.queryparameter.exceptions;

public class QueryParserException extends RuntimeException {

  public QueryParserException(final String message) {
    super(message);
  }

  public QueryParserException(final Exception ex) {
    super(ex);
  }

}
