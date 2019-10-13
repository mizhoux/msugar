package xyz.mizhoux.sugar.function;

/**
 * CheckedBiConsumer
 *
 * @author 之叶
 * @date   2019/10/13
 */
public interface CheckedBiConsumer<T, U> {

    void accept(T t, U u) throws Throwable;

}
