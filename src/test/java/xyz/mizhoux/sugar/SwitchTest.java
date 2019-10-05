package xyz.mizhoux.sugar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * SwitchTest
 *
 * @author 之叶
 * @date   2019/10/05
 */
public class SwitchTest {

    @Test
    public void testAccept() {
        int input = 3;
        Switch.on(input)
                .is(0).thenAccept(i -> System.out.println("i is 0"))
                .is(1).thenAccept(i -> System.out.println("i is 1"))
                .is(2).thenAccept(i -> System.out.println("i is 2"))
                .elseAccept(i -> System.out.println("i >= 3"));
    }

    @Test
    public void testEvaluate() {
        assertEquals(-1, parseLong(null));
        assertEquals(123L, parseLong(123L));
        assertEquals(123L, parseLong("123"));
        assertEquals(0L, parseLong(true));
    }

    private long parseLong(Object input) {
        return Switch.on(input, Long.class)
                .is(null).thenGet(-1L)
                .when(Long.class::isInstance)
                .thenApply(v -> Long.class.cast(v))
                .when(String.class::isInstance)
                .thenApply(v -> Long.valueOf((String) v))
                .elseGet(0L);
    }
}
