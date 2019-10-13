package xyz.mizhoux.sugar;

import xyz.mizhoux.sugar.function.*;

import java.util.Objects;
import java.util.function.*;

/**
 * 包装受检函数为非受检函数
 *
 * @author 之叶
 * @date   2019/10/06
 */
public interface Attempt {

    /**
     * 包装受检的 Function
     *
     * @param function 受检的 Function
     * @param <T>
     * @param <R>
     * @return 非受检的 Function
     */
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

    /**
     * 包装受检的 Function，并自定义异常处理
     *
     * @param function 受检的 Function
     * @param handler  自定义异常处理
     * @param <T>
     * @param <R>
     * @return 非受检的 Function
     */
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

    /**
     * 包装受检的 BiFunction
     *
     * @param function 受检的 BiFunction
     * @param <T>
     * @param <U>
     * @param <R>
     * @return 非受检的 BiFunction
     */
    static <T, U, R> BiFunction<T, U, R> apply(CheckedBiFunction<T, U, R> function) {
        Objects.requireNonNull(function);

        return (t, u) -> {
            try {
                return function.apply(t, u);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * 包装受检的 BiFunction，并自定义异常处理
     *
     * @param function 受检的 BiFunction
     * @param handler  自定义异常处理
     * @param <T>
     * @param <U>
     * @param <R>
     * @return 非受检的 BiFunction
     */
    static <T, U, R> BiFunction<T, U, R> apply(CheckedBiFunction<T, U, R> function, Function<Throwable, R> handler) {
        Objects.requireNonNull(function);
        Objects.requireNonNull(handler);

        return (t, u) -> {
            try {
                return function.apply(t, u);
            } catch (Throwable e) {
                return handler.apply(e);
            }
        };
    }

    /**
     * 包装受检的 Consumer
     *
     * @param consumer 受检的 Consumer
     * @param <T>
     * @return 非受检的 Consumer
     */
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

    /**
     * 包装受检的 Consumer，并自定义异常处理
     *
     * @param consumer 受检的 Consumer
     * @param handler  自定义异常处理
     * @param <T>
     * @return 非受检的 Consumer
     */
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

    /**
     * 包装受检的 BiConsumer
     *
     * @param biConsumer 受检的 BiConsumer
     * @param <T>
     * @param <U>
     * @return 非受检的 BiConsumer
     */
    static <T, U> BiConsumer<T, U> accept(CheckedBiConsumer<T, U> biConsumer) {
        Objects.requireNonNull(biConsumer);

        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * 包装受检的 BiConsumer，并自定义异常处理
     *
     * @param biConsumer 受检的 BiConsumer
     * @param handler    自定义异常处理
     * @param <T>
     * @param <U>
     * @return 非受检的 BiConsumer
     */
    static <T, U> BiConsumer<T, U> accept(CheckedBiConsumer<T, U> biConsumer, Consumer<Throwable> handler) {
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(handler);

        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Throwable e) {
                handler.accept(e);
            }
        };
    }

    /**
     * 包装受检的 Supplier
     *
     * @param supplier 受检的 Supplier
     * @param <R>
     * @return 非受检的 Supplier
     */
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

    /**
     * 包装受检的 Supplier，并并自定义异常处理
     *
     * @param supplier 受检的 Supplier
     * @param handler  自定义异常处理
     * @param <R>
     * @return 非受检的 Supplier
     */
    static <R> Supplier<R> supply(CheckedSupplier<R> supplier, Function<Throwable, R> handler) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(handler);

        return () -> {
            try {
                return supplier.supply();
            } catch (Throwable e) {
                return handler.apply(e);
            }
        };
    }

}
