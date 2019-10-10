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
        int input = 1;
        Switch.on(input)
                .is(0).thenAccept(i -> System.out.println("input is 0"))
                .is(1).thenAccept(i -> System.out.println("input is 1"))
                .is(2).thenAccept(i -> System.out.println("input is 2"))
                .elseAccept(i -> System.out.println("input < 0 || input > 3"));
    }

    @Test
    public void testEvaluate() {
        assertEquals(-1, parseLong(null));
        assertEquals(123L, parseLong(123L));
        assertEquals(123L, parseLong("123"));
        assertEquals(0L, parseLong(true));

        assertEquals("zero", transform(0));
        assertEquals("one", transform(1));
        assertEquals("two", transform(2));
        assertEquals("many", transform(3));
    }

    @Test
    public void testIn() {
        assertEquals(0, getStringType(null));
        assertEquals(0, getStringType(""));

        assertEquals(1, getStringType("null"));
        assertEquals(1, getStringType("empty"));
        assertEquals(1, getStringType("blank"));

        assertEquals(2, getStringType("^$"));
        assertEquals(2, getStringType("abc"));
    }

    private int getStringType(String value) {
        return Switch.on(value)
                .out(Integer.class)
                .in(null, "").thenGet(0)
                .in(null, "", "null", "empty", "blank").thenGet(1)
                .elseGet(2);
    }

    private long parseLong(Object input) {
        return Switch.on(input)
                .out(Long.class)
                .is(null)
                .thenGet(-1L)
                .when(Long.class::isInstance)
                .thenApply(v -> Long.class.cast(v))
                .when(String.class::isInstance)
                .thenApply(v -> Long.valueOf((String) v))
                .elseGet(0L);
    }

    private String transform(int i) {
        return Switch.on(i)
                .out(String.class)
                .is(0).thenGet("zero")
                .is(1).thenGet("one")
                .is(2).thenGet("two")
                .elseGet("many");
    }
}
