package xyz.mizhoux.sugar;

import xyz.mizhoux.sugar.function.UncheckedConsumer;
import xyz.mizhoux.sugar.function.UncheckedFunction;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Try
 *
 * @author 之叶
 * @date   2019/10/06
 */
public interface Try {

    static <T, R> Function<T, R> apply(UncheckedFunction<T, R> function) {
        Objects.requireNonNull(function);

        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T, R> Function<T, R> apply(UncheckedFunction<T, R> function, Function<Exception, R> handler) {
        Objects.requireNonNull(function);
        Objects.requireNonNull(handler);

        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                return handler.apply(e);
            }
        };
    }

    static <T> Consumer<T> accept(UncheckedConsumer<T> consumer) {
        Objects.requireNonNull(consumer);

        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T> Consumer<T> accept(UncheckedConsumer<T> consumer, Consumer<Exception> handler) {
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(handler);

        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                handler.accept(e);
            }
        };
    }

}
