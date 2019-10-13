package xyz.mizhoux.sugar.function;

/**
 * CheckedBiFunction
 *
 * @author 之叶
 * @date   2019/10/13
 */
public interface CheckedBiFunction<T, U, R> {

    R apply(T t, U u) throws Throwable;

}
