package xyz.mizhoux.sugar.function;

/**
 * CheckedFunction
 *
 * @author 之叶
 * @date   2019/10/06
 */
@FunctionalInterface
public interface CheckedFunction<T, R> {

    R apply(T input) throws Throwable;

}
