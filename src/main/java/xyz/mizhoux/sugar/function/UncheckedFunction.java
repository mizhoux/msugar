package xyz.mizhoux.sugar.function;

/**
 * UncheckedFunction
 *
 * @author 之叶
 * @date   2019/10/06
 */
@FunctionalInterface
public interface UncheckedFunction<T, R> {

    R apply(T input) throws Exception;

}
