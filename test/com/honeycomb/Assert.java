package com.honeycomb;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public final class Assert {

    public static <T> void assertOptionalPresent(
            T expected,
            Optional<T> optional) {
        assertTrue(optional.isPresent());
        assertEquals(expected, optional.get());
    }

    public static void assertOptionalEmpty(Optional<?> optional) {
        assertFalse(optional.isPresent());
    }
}
