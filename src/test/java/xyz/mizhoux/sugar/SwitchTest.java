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
    public void testLogic() {
        assertEquals(0, getStringType1(null));
        assertEquals(0, getStringType1(""));
        assertEquals(0, getStringType1("  "));
        assertEquals(1, getStringType1("null"));
        assertEquals(1, getStringType1("empty"));
        assertEquals(1, getStringType1("blank"));
        assertEquals(1, getStringType1("..."));
        assertEquals(2, getStringType1("^$"));
        assertEquals(2, getStringType1("^abc$"));
        assertEquals(3, getStringType1("^abc"));
        assertEquals(3, getStringType1("abc$"));
    }

    @Test
    public void testIn() {
        assertEquals(0, getStringType2(null));
        assertEquals(0, getStringType2(""));
        assertEquals(1, getStringType2("null"));
        assertEquals(1, getStringType2("empty"));
        assertEquals(1, getStringType2("blank"));
        assertEquals(1, getStringType2("..."));
        assertEquals(2, getStringType2("^$"));
        assertEquals(2, getStringType2("^abc$"));
        assertEquals(2, getStringType2("^abc"));
        assertEquals(2, getStringType2("abc$"));
    }

    private int getStringType2(String value) {
        return Switch.on(value, Integer.class)
                .in(null, "").thenGet(0)
                .in("null", "empty", "blank", "...").thenGet(1)
                .elseGet(2);
    }

    private int getStringType1(String value) {
        return Switch.on(value, Integer.class)
                .is(null).or(s -> s.trim().isEmpty()).thenGet(0)
                .is("null").or("empty").or("blank").or("...").thenGet(1)
                .when(s -> s.startsWith("^")).and(s -> s.endsWith("$")).thenGet(2)
                .elseGet(3);
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

    private String transform(int i) {
        return Switch.on(i, String.class)
                .is(0).thenGet("zero")
                .is(1).thenGet("one")
                .is(2).thenGet("two")
                .elseGet("many");
    }
}
