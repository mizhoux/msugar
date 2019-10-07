package xyz.mizhoux.sugar;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * TriesTest
 *
 * @author 之叶
 * @date   2019/10/07
 */
public class TriesTest {

    @Test
    public void testAccept() {
        Consumer<Object> action = Tries.accept(this::throwableAccept);
        assertNotNull(action);
    }

    @Test(expected = RuntimeException.class)
    public void testApply() {
        Function<Object, Object> mapper = Tries.apply(this::throwableApply);
        assertNotNull(mapper);

        mapper.apply(null);
    }

    @Test
    public void testApplyWithHandler() {
        Function<Object, Integer> mapper = Tries.apply(this::throwableApply, ex -> 0);
        assertNotNull(mapper);

        Object result = mapper.apply(null);
        assertEquals(0, result);
    }

    private void throwableAccept(Object value) throws Exception {
        throw new Exception("throwableAccept");
    }

    private int throwableApply(Object value) throws Exception {
        throw new Exception("throwableApply");
    }
}
