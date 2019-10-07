package xyz.mizhoux.sugar.function;

/**
 * CheckedSupplier
 *
 * @author 之叶
 * @date   2019/10/07
 */
@FunctionalInterface
public interface CheckedSupplier<R> {

    R supply() throws Throwable;

}
