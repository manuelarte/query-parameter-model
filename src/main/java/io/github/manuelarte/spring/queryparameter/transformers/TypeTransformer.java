package io.github.manuelarte.spring.queryparameter.transformers;

/**
 * Transform the in parameter to the out parameter.
 *
 * @param <IN>  Can be a String or List<String>
 * @param <OUT> the converted value to the desired type
 */
public interface TypeTransformer<IN, OUT> {

  OUT transformValue(Class<?> entity, String criterionKey, IN value);

}
