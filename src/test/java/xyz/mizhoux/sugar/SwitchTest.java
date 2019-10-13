package xyz.mizhoux.sugar;

import org.junit.Test;

import static org.junit.Assert.*;

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

        assertEquals("zero", get(0));
        assertEquals("one", get(1));
        assertEquals("two", get(2));
        assertEquals("many", get(3));

        assertEquals("zero", apply(0));
        assertEquals("one", apply(1));
        assertEquals("two", apply(2));
        assertEquals("many", apply(3));

        assertEquals("zero", supply(0));
        assertEquals("one", supply(1));
        assertEquals("two", supply(2));
        assertEquals("many", supply(3));
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

    @Test
    public void testObtain() {
        assertFalse(Switch.input(null).obtain().isPresent());
        assertTrue(Switch.input(null).is(null).thenGet("").obtain().isPresent());
    }

    private int getStringType(String value) {
        return Switch.input(value)
                .output(Integer.class)
                .in(null, "").thenGet(0)
                .in(null, "", "null", "empty", "blank").thenGet(1)
                .elseGet(2);
    }

    private long parseLong(Object input) {
        return Switch.input(input)
                .output(Long.class)
                .is(null)
                .thenGet(-1L)
                .when(Long.class::isInstance)
                .thenApply(v -> (Long) v)
                .when(String.class::isInstance)
                .thenApply(v -> Long.valueOf((String) v))
                .elseGet(0L);
    }

    private String get(int i) {
        return Switch.input(i)
                .output(String.class)
                .is(0).thenGet("zero")
                .is(1).thenGet("one")
                .is(2).thenGet("two")
                .elseGet("many");
    }

    private String apply(int i) {
        return Switch.input(i)
                .output(String.class)
                .is(0).thenApply(v -> "zero")
                .is(1).thenApply(v -> "one")
                .is(2).thenApply(v -> "two")
                .elseApply(v -> "many");
    }

    private String supply(int i) {
        return Switch.input(i)
                .output(String.class)
                .is(0).thenSupply(() -> "zero")
                .is(1).thenSupply(() -> "one")
                .is(2).thenSupply(() -> "two")
                .elseSupply(() -> "many");
    }
}
