package io.github.manuelarte.spring.queryparameter.transformers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.manuelarte.spring.queryparameter.model.QueryParameterArgumentResolver;
import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import io.github.manuelarte.spring.queryparameter.transformers.ClassFieldTransformerImplTest.ItConfiguration;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest(classes = ClassFieldTransformerImplTest.class)
@EnableWebMvc
@Import({ItConfiguration.class, QueryParameterArgumentResolver.class })
@ComponentScan("io.github.manuelarte.spring.queryparameter")
public class ClassFieldTransformerImplTest {

  @Autowired
  @Qualifier(("defaultTypeTransformer"))
  private ClassFieldTransformerImpl classFieldTransformer;

  @Test
  void testStringConversion() {
    final ExampleEntity exampleEntity = createExample();
    final Object value = classFieldTransformer.transformValue(exampleEntity.getClass(),
        "firstName", "test");
    assertEquals("test", value);
  }

  @Test
  void testIntegerConversion() {
    final ExampleEntity exampleEntity = createExample();
    final Object value = classFieldTransformer.transformValue(exampleEntity.getClass(),
        "age", "28");
    assertEquals(28, value);
  }

  @Test
  @Disabled("it's expecting something like 1/30/20")
  void testLocalDateConversion() {
    final ExampleEntity exampleEntity = createExample();
    final Object value = classFieldTransformer.transformValue(exampleEntity.getClass(),
        "birthDate", "2020-01-01");
    assertEquals(LocalDate.of(2020, 1, 1), value);
  }

  @Test
  void testEnumConversion() {
    final ExampleEntity exampleEntity = createExample();
    final Gender expected = Gender.PREFER_NOT_TO_SAY;
    final Object value = classFieldTransformer.transformValue(exampleEntity.getClass(),
        "gender", expected.toString());
    assertEquals(expected, value);
  }

  @Test
  void testInstantConversion() {
    final ExampleEntity exampleEntity = createExample();
    final Instant expected = Instant.now();
    final Object value = classFieldTransformer.transformValue(exampleEntity.getClass(),
        "createdAt", expected.toString());
    assertEquals(expected, value);
  }

  private ExampleEntity createExample() {
    final ExampleEntity parent = new ExampleEntity();
    parent.firstName = "Hola";
    parent.age = 20;
    parent.birthDate = LocalDate.of(1986, 2, 8);
    parent.gender = Gender.MALE;
    parent.createdAt = Instant.now();
    return parent;
  }

  public enum Gender {
    MALE,
    FEMALE,
    PREFER_NOT_TO_SAY;
  }

  public static class ExampleEntity {

    private String firstName;
    private int age;
    private LocalDate birthDate;
    private Gender gender;
    private Instant createdAt;
  }

  @Configuration
  public static class ItConfiguration {
    @Bean
    public QueryCriteriaTransformer<QueryCriteria> queryCriteriaQueryCriteriaTransformer() {
      return new QueryCriteriaTransformer<QueryCriteria>() {
        @Override
        public QueryCriteria apply(final Class<?> aClass, final QueryCriteria queryCriteria) {
          return queryCriteria;
        }
        @Override
        public boolean supports(Class<?> clazz) {
          return QueryCriteria.class.equals(clazz);
        }
      };
    }
  }

}
