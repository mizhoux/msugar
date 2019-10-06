package xyz.mizhoux.sugar.function;

/**
 * UncheckedConsumer
 *
 * @author 之叶
 * @date   2019/10/06
 */
@FunctionalInterface
public interface UncheckedConsumer<T> {

    void accept(T input) throws Exception;

}
