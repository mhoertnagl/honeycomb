package com.honeycomb;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class Assert {

    public static <T> void assertValid(T expected, State<? extends T> state) {
        assertTrue(state.isValid());
        assertEquals(expected, state.val().orElse(null));
    }

    public static void assertError(State<?> state) {
        assertFalse(state.isValid());
    }

    public static void assertError(State<?> state, String... errors) {
        assertFalse(state.isValid());
        assertEquals(Arrays.stream(errors).toList(), state.errors());
    }
}
