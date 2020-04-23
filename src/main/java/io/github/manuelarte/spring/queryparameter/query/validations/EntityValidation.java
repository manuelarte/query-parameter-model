package io.github.manuelarte.spring.queryparameter.query.validations;

import static java.util.stream.Collectors.groupingBy;

import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import io.github.manuelarte.spring.queryparameter.query.QueryCriterion;
import io.github.manuelarte.spring.queryparameter.query.constraints.QueryParamNotNull;
import io.github.manuelarte.spring.queryparameter.query.constraints.QueryParamNull;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import lombok.val;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.NullDef;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.springframework.util.ReflectionUtils;

/**
 * Validator based on the annotations attached in the entity.
 */
public class EntityValidation {

  private final QueryCriteria queryCriteria;
  private final Class<?> entityClass;
  private final Map<String, List<QueryCriterion<Object>>> groupedByKey;

  public EntityValidation(final QueryCriteria queryCriteria, final Class<?> entityClass) {
    this.queryCriteria = queryCriteria;
    this.entityClass = entityClass;
    this.groupedByKey = groupByKey();
  }

  public Set<ConstraintViolation<Object>> validate(final Class<?>... groups) {
    final Set<ConstraintViolation<Object>> constraints = new HashSet<>();
    ReflectionUtils.doWithFields(entityClass, field -> {
      if (field.getAnnotation(QueryParamNotNull.class) != null) {
        final QueryParamNotNull notNull = field.getAnnotation(QueryParamNotNull.class);
        constraints.addAll(applyConstraintsValidation(field, notNullDef(field, notNull), groups));
      }
      if (field.getAnnotation(QueryParamNotNull.List.class) != null) {
        final QueryParamNotNull.List notNulls = field.getAnnotation(QueryParamNotNull.List.class);
        Arrays.stream(notNulls.value()).forEach(
            an -> constraints.addAll(applyConstraintsValidation(field, notNullDef(field, an), groups)));
      }
      if (field.getAnnotation(QueryParamNull.class) != null) {
        final QueryParamNull nullAn = field.getAnnotation(QueryParamNull.class);
        constraints.addAll(applyConstraintsValidation(field, nullDef(field, nullAn), groups));
      }
      if (field.getAnnotation(QueryParamNull.List.class) != null) {
        final QueryParamNull.List nulls = field.getAnnotation(QueryParamNull.List.class);
        Arrays.stream(nulls.value()).forEach(an -> {
          constraints.addAll(applyConstraintsValidation(field, nullDef(field, an), groups));
        });
      }
    });
    return constraints;
  }

  private NotNullDef notNullDef(final Field field, final QueryParamNotNull queryParamNotNull) {
    return new NotNullDef()
        .groups(queryParamNotNull.groups())
        .message(String.format(queryParamNotNull.message(), field.getName()))
        .payload(queryParamNotNull.payload());
  }

  private NullDef nullDef(final Field field, final QueryParamNull queryParamNotNull) {
    return new NullDef()
        .groups(queryParamNotNull.groups())
        .message(String.format(queryParamNotNull.message(), field.getName()))
        .payload(queryParamNotNull.payload());
  }

  private Set<ConstraintViolation<Object>> applyConstraintsValidation(final Field field,
      final ConstraintDef<?, ?> constraint, final Class<?>... groups) {
    final Set<ConstraintViolation<Object>> constraints = new HashSet<>();
    val criteriaOfKeys = getCriterionOfKeys(field.getName());
    criteriaOfKeys.forEach(criterion -> constraints.addAll(applyValidation(
        Collections.singletonList(constraint), criterion, groups)));
    return constraints;
  }

  private Set<ConstraintViolation<Object>> applyValidation(
      final List<ConstraintDef<?, ?>> definitions,
      final QueryCriterion<Object> queryCriterion,
      final Class<?>... groups) {
    final ConstraintMapping mapping = new DefaultConstraintMapping();
    val value = mapping.type(queryCriterion.getClass())
        .property("value", ElementType.FIELD);
    definitions.forEach(it -> value.constraint(it));
    val config = Validation.byProvider(HibernateValidator.class).configure();
    config.addMapping(mapping);
    val factory = config.buildValidatorFactory();
    val validator = factory.getValidator();
    return validator.validate(queryCriterion, groups);
  }



  private Map<String, List<QueryCriterion<Object>>> groupByKey() {
    return StreamSupport.stream(this.queryCriteria.spliterator(), false)
        .collect(groupingBy(QueryCriterion::getKey));
  }

  private List<QueryCriterion<Object>> getCriterionOfKeys(final String key) {
    return Optional.ofNullable(groupedByKey.get(key)).orElseGet(
        () -> Collections.singletonList(new QueryCriterion<>(key, null, null)));
  }

}
