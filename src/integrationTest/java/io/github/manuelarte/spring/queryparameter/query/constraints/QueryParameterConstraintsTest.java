package io.github.manuelarte.spring.queryparameter.query.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.manuelarte.spring.queryparameter.config.QueryCriteriaConfig;
import io.github.manuelarte.spring.queryparameter.config.QueryCriteriaParser;
import io.github.manuelarte.spring.queryparameter.model.QueryCriteriaParserContext;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerRegistry;
import io.github.manuelarte.spring.queryparameter.query.validations.EntityValidation;
import java.util.Set;
import javax.validation.ConstraintViolation;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = QueryParameterConstraintsTest.class)
@EnableAutoConfiguration
@Import({TypeTransformerRegistry.class, QueryCriteriaConfig.class})
public class QueryParameterConstraintsTest {

  @Autowired
  private QueryCriteriaParser queryCriteriaParser;

  @Test
  void testDefaultValidationGroup() {
    final EntityExample entity = new EntityExample();
    entity.firstName = "Manuel";
    final String q = "firstName::Manuel";
    val queryCriteria = queryCriteriaParser.parse(q,
        new QueryCriteriaParserContext(null, null));
    Set<ConstraintViolation<Object>> validate = new EntityValidation(queryCriteria,
        EntityExample.class).validate();
    assertTrue(validate.isEmpty());
  }

  @Test
  void testDefaultAndTest1ValidationGroup() {
    final EntityExample entity = new EntityExample();
    entity.firstName = "Manuel";
    final String q = "firstName::Manuel";
    val queryCriteria = queryCriteriaParser.parse(q,
        new QueryCriteriaParserContext(null, null));
    Set<ConstraintViolation<Object>> validate = new EntityValidation(queryCriteria,
        EntityExample.class).validate(Test1.class);
    assertFalse(validate.isEmpty());
  }

  @Test
  void testNotAllowedKey() {
    final EntityExample entity = new EntityExample();
    entity.age = 34;
    final String q = "age::34";
    val queryCriteria = queryCriteriaParser.parse(q,
        new QueryCriteriaParserContext(null, null));
    Set<ConstraintViolation<Object>> validate = new EntityValidation(queryCriteria,
        EntityExample.class).validate(Test1.class);
    assertFalse(validate.isEmpty());
  }

  public interface Test1 { }

  public static class EntityExample {
    @QueryParamNotNull
    private String firstName;
    @QueryParamNotNull(groups = Test1.class)
    private String lastName;
    @QueryParamNull(groups = Test1.class)
    private int age;
  }

}
