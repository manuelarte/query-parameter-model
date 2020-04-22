package io.github.manuelarte.spring.queryparameter.validations;

import io.github.manuelarte.spring.queryparameter.operators.EqualsOperator;
import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import io.github.manuelarte.spring.queryparameter.query.QueryCriterion;
import io.github.manuelarte.spring.queryparameter.query.constraints.QueryParamNotNull;
import io.github.manuelarte.spring.queryparameter.query.validations.EntityValidation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class EntityValidationTest {

  @Test
  @Disabled("Hibernate classes not found in runtime")
  void test() {
    final QueryCriteria queryCriteria = new QueryCriteria(
        new QueryCriterion<>("firstName", new EqualsOperator(), "Manuel"));
    final EntityValidation entityValidation = new EntityValidation(queryCriteria, EntityExample.class);
    // entityValidation.validate();
  }

  public static class EntityExample {
    @QueryParamNotNull
    private String firstName;
    private String lastName;
    private int age;
  }

}
