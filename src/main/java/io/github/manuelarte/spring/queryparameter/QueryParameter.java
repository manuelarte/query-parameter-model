package io.github.manuelarte.spring.queryparameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@SuppressWarnings("unused")
public @interface QueryParameter {

  Class<?> entity();

  String paramName() default "q";

  String[] allowedKeys() default {};

  String[] notAllowedKeys() default {};

}
