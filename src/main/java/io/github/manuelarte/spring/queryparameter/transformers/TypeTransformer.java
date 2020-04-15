package io.github.manuelarte.spring.queryparameter.transformers;

/**
 * Transform the in parameter to the out parameter.
 *
 * @param <I>  Can be a String or {@code List<String>}
 * @param <O> the converted value to the desired type
 */
public interface TypeTransformer<I, O> {

  O transformValue(Class<?> entity, String criterionKey, I value);

}
