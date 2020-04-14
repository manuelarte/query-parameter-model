package io.github.manuelarte.spring.queryparameter.query;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class QueryCriterionTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.forClass(QueryCriterion.class)
        .verify();
  }

}
