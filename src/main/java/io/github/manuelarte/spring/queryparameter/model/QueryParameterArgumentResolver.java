package io.github.manuelarte.spring.queryparameter.model;

import io.github.manuelarte.spring.queryparameter.QueryParameter;
import io.github.manuelarte.spring.queryparameter.config.QueryCriteriaParser;
import io.github.manuelarte.spring.queryparameter.exceptions.QueryParserException;
import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import io.github.manuelarte.spring.queryparameter.transformers.QueryCriteriaTransformer;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@lombok.AllArgsConstructor
public class QueryParameterArgumentResolver implements HandlerMethodArgumentResolver {

  private final QueryCriteriaParser queryCriteriaParser;
  private final QueryCriteriaTransformer<?> queryCriteriaTransformer;

  @Override
  public boolean supportsParameter(final MethodParameter parameter) {
    return parameter.getParameterAnnotation(QueryParameter.class) != null;
  }

  @Override
  @SuppressWarnings("GetClassOnClass")
  public Object resolveArgument(final MethodParameter parameter,
      final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest,
      final WebDataBinderFactory binderFactory) {
    final QueryParameter queryParameter = parameter.getParameterAnnotation(QueryParameter.class);
    final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

    final String q = request.getParameter(queryParameter.paramName());
    if (q != null && !q.isEmpty()) {
      final Class<?> documentEntity = queryParameter.entity();
      final QueryCriteriaParserContext context = createContext(queryParameter);
      final QueryCriteria queryCriteria = queryCriteriaParser.parse(q, context);
      final Object query = queryCriteriaTransformer.apply(documentEntity, queryCriteria);
      if (queryCriteriaTransformer.supports(parameter.getParameterType())) {
        return query;
      } else if (isValidOptional(parameter)) {
        return Optional.of(query);
      } else {
        throw new QueryParserException("Don't know how to parse to the class "
            + parameter.getParameterType().getClass());
      }
    } else {
      if (queryCriteriaTransformer.supports(parameter.getParameterType())) {
        return null;
      } else if (isValidOptional(parameter)) {
        return Optional.empty();
      } else {
        throw new QueryParserException("Don't know how to parse to the class "
            + parameter.getParameterType().getClass());
      }
    }
  }

  private QueryCriteriaParserContext createContext(final QueryParameter annotation) {
    final Set<String> allowedKeys =
        annotation.allowedKeys().length > 0
            ? new HashSet<>(Arrays.asList(annotation.allowedKeys()))
            : null;
    final Set<String> notAllowedKeys =
        annotation.notAllowedKeys().length > 0
            ? new HashSet<>(Arrays.asList(annotation.notAllowedKeys()))
            : null;
    return new QueryCriteriaParserContext(allowedKeys, notAllowedKeys);
  }

  private boolean isValidOptional(final MethodParameter parameter) {
    return (parameter.getParameterType().equals(Optional.class)
        && parameter.getGenericParameterType() instanceof ParameterizedType);
  }

}
