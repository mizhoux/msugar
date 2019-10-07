package xyz.mizhoux.sugar.function;

/**
 * CheckedConsumer
 *
 * @author 之叶
 * @date   2019/10/06
 */
@FunctionalInterface
public interface CheckedConsumer<T> {

    void accept(T input) throws Throwable;

}
