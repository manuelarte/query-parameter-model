package io.github.manuelarte.spring.queryparameter.query.constraints;

import io.github.manuelarte.spring.queryparameter.query.constraints.QueryParamNull.List;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Payload;
import javax.validation.constraints.Null;

/**
 * Annotation to indicates that the key is mandatory in the query param
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface QueryParamNull {

  String message() default "%s: Query param key is not allowed";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

  @Documented
  @Target({ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    QueryParamNull[] value();
  }

}
