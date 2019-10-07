package xyz.mizhoux.sugar;

import xyz.mizhoux.sugar.function.CheckedConsumer;
import xyz.mizhoux.sugar.function.CheckedFunction;
import xyz.mizhoux.sugar.function.CheckedSupplier;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * TryTo
 *
 * @author 之叶
 * @date   2019/10/06
 */
public interface TryTo {

    static <T, R> Function<T, R> apply(CheckedFunction<T, R> function) {
        Objects.requireNonNull(function);

        return t -> {
            try {
                return function.apply(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T, R> Function<T, R> apply(CheckedFunction<T, R> function, Function<Throwable, R> handler) {
        Objects.requireNonNull(function);
        Objects.requireNonNull(handler);

        return t -> {
            try {
                return function.apply(t);
            } catch (Throwable e) {
                return handler.apply(e);
            }
        };
    }

    static <T> Consumer<T> accept(CheckedConsumer<T> consumer) {
        Objects.requireNonNull(consumer);

        return t -> {
            try {
                consumer.accept(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T> Consumer<T> accept(CheckedConsumer<T> consumer, Consumer<Throwable> handler) {
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(handler);

        return t -> {
            try {
                consumer.accept(t);
            } catch (Throwable e) {
                handler.accept(e);
            }
        };
    }

    static <R> Supplier<R> supply(CheckedSupplier<R> supplier) {
        Objects.requireNonNull(supplier);

        return () -> {
            try {
                return supplier.supply();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <R> Supplier<R> supply(CheckedSupplier<R> supplier, Function<Throwable, R> handler) {
        Objects.requireNonNull(supplier);

        return () -> {
            try {
                return supplier.supply();
            } catch (Throwable e) {
                return handler.apply(e);
            }
        };
    }

}
