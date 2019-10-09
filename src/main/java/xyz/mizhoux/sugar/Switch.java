package xyz.mizhoux.sugar;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
     * 是否已经存在过满足的条件
     */
    private boolean met;

    private Switch(T input) {
        this.input = input;
    }

    /**
     * 在指定的输入上使用 Switch，返回用于消费的 Switch 实例
     *
     * @param input 指定的输入
     * @param <T> 输入类型
     * @param <R> 输出类型
     * @return 用于消费的 Switch 实例
     */
    public static <T, R> Switch<T, R> on(T input) {
        return new Switch<>(input);
    }

    /**
     * 在指定的输入上使用 Switch，返回用于求值的 Switch 实例
     *
     * @param input 指定的输入
     * @param outType 所求值的 Class
     * @param <T> 输入类型
     * @param <R> 输出类型
     * @return 用于求值的 Switch 实例
     */
    public static <T, R> Switch<T, R> on(T input, Class<R> outType) {
        return new Switch<>(input);
    }

    /**
     * 设定当前 Switch 的输出值的类型
     *
     * @param outType 输出值的类型
     * @param <V> 输出值的类型
     * @return 当前的 Switch 实例
     */
    @SuppressWarnings("unchecked")
    public <V> Switch<T, V> output(Class<V> outType) {
        return (Switch<T, V>) this;
    }

    /**
     * 判断输入是否和给定的目标相等
     *
     * @param target 给定的目标
     * @return 当前 Switch 实例
     */
    public Switch<T, R> is(T target) {
        // 判断输入是否和 target 相等
        return when(Predicate.isEqual(target));
    }

    /**
     * 判断输入是否存在给定的一群值中
     *
     * @param values 给定的一群值
     * @return 当前 Switch 实例
     */
    public Switch<T, R> in(T... values) {
        Objects.requireNonNull(values);

        return when(e -> {
            for (T value : values) {
                if (Objects.equals(e, value)) {
                    return true;
                }
            }

            return false;
        });
    }

    /**
     * 设定输入需要满足的条件
     *
     * @param condition 输入需要满足的条件
     * @return 当前 Switch 实例
     */
    public Switch<T, R> when(Predicate<T> condition) {
        if (met) { return this; }

        this.condition = Objects.requireNonNull(condition);
        return this;
    }

    /**
     * 满足某个条件时，对输入进行消费操作
     *
     * @param action 消费动作
     * @return 当前 Switch 实例
     */
    public Switch<T, R> thenAccept(Consumer<T> action) {
        if (met) { return this; }

        requireNonNullArgAndCondition(action);

        if (condition.test(input)) {
            action.accept(input);

            // 标记已经存在过满足的条件
            met = true;
        }

        return this;
    }

    /**
     * 不满足任一条件时，对输入进行消费操作
     *
     * @param action 消费动作
     */
    public void elseAccept(Consumer<T> action) {
        if (met) { return; }

        Objects.requireNonNull(action);
        // 之前没有任何一个条件被满足
        action.accept(input);
    }

    /**
     * 满足某个条件时，进行求值操作
     *
     * @param value 指定的输出值
     * @return 当前 Switch 实例
     */
    public Switch<T, R> thenGet(R value) {
        if (met) { return this; }

        requireNonNullCondition();

        // 满足条件
        if (condition.test(input)) {
            output = value;

            // 标记已经存在输出
            met = true;
        }

        return this;
    }

    /**
     * 不满足任一条件时，进行求值操作
     *
     * @param value 指定的输出值
     * @return 如果某个条件被满足，则返回满足条件时所求的值；否则返回指定的输出值
     */
    public R elseGet(R value) {
        return met ? output : value;
    }

    /**
     * 满足某个条件时，使用 Function 进行求值操作，当前 Switch 实例的输入会作为 Function 的输入
     *
     * @param mapper 指定的 Function
     * @return 当前 Switch 实例
     */
    public Switch<T, R> thenApply(Function<T, R> mapper) {
        if (met) { return this; }

        requireNonNullArgAndCondition(mapper);

        if (condition.test(input)) {
            output = mapper.apply(input);

            // 标记已经存在输出
            met = true;
        }

        return this;
    }

    /**
     * 不满足任一条件时，使用 Function 进行求值操作，当前 Switch 实例的输入会作为 Function 的输入
     *
     * @param mapper 指定的 Function
     * @return 如果某个条件被满足，则返回满足条件时所求的值；否则返回指定的 Function 产生的输出值
     */
    public R elseApply(Function<T, R> mapper) {
        Objects.requireNonNull(mapper);

        return met ? output : mapper.apply(input);
    }

    /**
     * 满足某个条件时，使用 Supplier 进行求值操作
     *
     * @param supplier 指定的 Supplier
     * @return 当前 Switch 实例
     */
    public Switch<T, R> thenSupply(Supplier<R> supplier) {
        if (met) { return this; }

        requireNonNullArgAndCondition(supplier);

        if (condition.test(input)) {
            output = supplier.get();

            // 标记已经存在输出
            met = true;
        }

        return this;
    }

    /**
     * 不满足任一条件时，使用 Supplier 进行求值操作
     *
     * @param supplier 指定的 Supplier
     * @return 如果某个条件被满足，则返回满足条件时所求的值；否则返回指定的 Supplier 产生的输出值
     */
    public R elseSupply(Supplier<R> supplier) {
        Objects.requireNonNull(supplier);

        return met ? output : supplier.get();
    }

    private void requireNonNullCondition() {
        if (condition == null) {
            throw new IllegalStateException("A condition must be set first.");
        }
    }

    private void requireNonNullArgAndCondition(Object arg) {
        Objects.requireNonNull(arg);
        requireNonNullCondition();
    }

}
