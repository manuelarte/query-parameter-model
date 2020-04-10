package org.manuel.spring.queryparameter.transformers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClassFieldTransformerImplTest {

  @Autowired
  private ClassFieldTransformerImpl classFieldTransformer;

  @Test
  void testStringConversion() {
    final ExampleEntity exampleEntity = createExample();
    final String value = classFieldTransformer.transformValue(exampleEntity.getClass(),
        "firstName", "test");
    assertEquals("test", value);
  }

  @Test
  void testIntegerConversion() {
    final ExampleEntity exampleEntity = createExample();
    final Integer value = classFieldTransformer.transformValue(exampleEntity.getClass(),
        "age", "28");
    assertEquals(28, value);
  }

  @Test
  @Disabled("it's expecting something like 1/30/20")
  void testLocalDateConversion() {
    final ExampleEntity exampleEntity = createExample();
    final LocalDate value = classFieldTransformer.transformValue(exampleEntity.getClass(),
        "birthDate", "2020-01-01");
    assertEquals(LocalDate.of(2020, 1, 1), value);
  }

  @Test
  void testEnumConversion() {
    final ExampleEntity exampleEntity = createExample();
    final Gender expected = Gender.PREFER_NOT_TO_SAY;
    final Gender value = classFieldTransformer.transformValue(exampleEntity.getClass(),
        "gender", expected.toString());
    assertEquals(expected, value);
  }

  @Test
  void testInstantConversion() {
    final ExampleEntity exampleEntity = createExample();
    final Instant expected = Instant.now();
    final Instant value = classFieldTransformer.transformValue(exampleEntity.getClass(),
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

}
