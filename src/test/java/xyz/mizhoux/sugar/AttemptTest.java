package xyz.mizhoux.sugar;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.*;

/**
 * AttemptTest
 *
 * @author 之叶
 * @date   2019/10/07
 */
public class AttemptTest {

    @Test(expected = RuntimeException.class)
    public void testAccept() {
        Consumer<Object> action = Attempt.accept(this::throwableAccept);
        assertNotNull(action);

        action.accept("");
    }

    @Test
    public void testAcceptWithHandler() {
        Consumer<Object> action = Attempt.accept(this::throwableAccept, ex -> {});
        assertNotNull(action);

        action.accept("");
    }

    @Test(expected = RuntimeException.class)
    public void testApply() {
        Function<Object, Object> mapper = Attempt.apply(this::throwableApply);
        assertNotNull(mapper);

        mapper.apply("");
    }

    @Test
    public void testApplyWithHandler() {
        Function<Object, Integer> mapper = Attempt.apply(this::throwableApply, ex -> 0);
        assertNotNull(mapper);

        Object result = mapper.apply("");
        assertEquals(0, result);
    }

    @Test(expected = RuntimeException.class)
    public void testSupply() {
        Supplier<Integer> supplier = Attempt.supply(this::throwableSupply);
        assertNotNull(supplier);

        supplier.get();
    }

    @Test
    public void testSupplyWithHandler() {
        Supplier<Integer> supplier = Attempt.supply(this::throwableSupply, ex -> 0);
        assertNotNull(supplier);

        assertEquals(0, supplier.get().intValue());
    }

    private void throwableAccept(Object value) throws Exception {
        throw new Exception("throwableAccept");
    }

    private int throwableApply(Object value) throws Exception {
        throw new Exception("throwableApply");
    }

    private int throwableSupply() throws Exception {
        throw new Exception("throwableSupply");
    }

}
