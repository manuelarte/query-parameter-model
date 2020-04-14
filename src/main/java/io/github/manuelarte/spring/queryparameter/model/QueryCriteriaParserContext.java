package io.github.manuelarte.spring.queryparameter.model;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class QueryCriteriaParserContext {

  private final Predicate<String> allowedKeys;

  public QueryCriteriaParserContext(@Nullable final Set<String> allowedKeys,
      @Nullable final Set<String> notAllowedKeys) {
    final Predicate<String> allowed = allowedKeys != null ? key -> allowedKeys.contains(key)
        : key -> true;
    final Predicate<String> notAllowed =
        notAllowedKeys != null ? key -> notAllowedKeys.contains(key)
            : key -> false;
    this.allowedKeys = allowed.and(notAllowed.negate());
  }

  public boolean allowedKey(final String key) {
    return this.allowedKeys.test(key);
  }

}
