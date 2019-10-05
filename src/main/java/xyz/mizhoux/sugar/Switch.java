package xyz.mizhoux.sugar;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Switch
 *
 * @author 之叶
 * @date   2019/10/05
 */
public class Switch<T, R> {

    /**
     * 输入
     */
    private final T input;

    /**
     * 输出
     */
    private R output;

    /**
     * 当前的条件
     */
    private Predicate<T> condition;

    /**
     * 是否已经存在输出
     */
    private boolean outputted;

    /**
     * 是否已经存在过满足的条件
     */
    private boolean accepted;

    public Switch(T input) {
        this.input = input;
    }

    public static <T, R> Switch<T, R> on(T input) {
        return new Switch<>(input);
    }

    public static <T, R> Switch<T, R> on(T input, Class<R> outType) {
        return new Switch<>(input);
    }

    public Switch<T, R> is(T target) {
        if (!accepted) {
            // 判断待检测目标是否和 target 相等
            condition = Predicate.isEqual(target);
        }

        return this;
    }

    public Switch<T, R> when(Predicate<T> condition) {
        Objects.requireNonNull(condition);

        if (!accepted) {
            this.condition = condition;
        }

        return this;
    }

    public Switch<T, R> thenAccept(Consumer<T> action) {
        Objects.requireNonNull(action);

        if (condition == null) {
            throw new IllegalStateException("A condition must be set first.");
        }

        if (!accepted && condition.test(input)) {
            action.accept(input);

            // 标记已经存在过满足的条件
            accepted = true;
        }

        return this;
    }

    public void elseAccept(Consumer<T> action) {
        Objects.requireNonNull(action);

        // 之前没有任何一个条件被满足
        if (!accepted) {
            action.accept(input);
        }
    }

    public Switch<T, R> thenGet(R value) {
        if (condition == null) {
            throw new IllegalStateException("A condition must be set first.");
        }

        // 满足条件
        if (!outputted && condition.test(input)) {
            output = value;

            // 标记已经存在输出
            outputted = true;
        }

        return this;
    }

    public Switch<T, R> thenApply(Function<T, R> mapper) {
        Objects.requireNonNull(mapper);

        if (condition == null) {
            throw new IllegalStateException("A condition must be set first.");
        }

        if (!outputted && condition.test(input)) {
            output = mapper.apply(input);

            // 标记已经存在输出
            outputted = true;
        }

        return this;
    }

    public R elseGet(R value) {
        return outputted ? output : value;
    }
}
