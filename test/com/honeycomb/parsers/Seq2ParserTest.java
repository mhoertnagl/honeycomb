package com.honeycomb.parsers;

import org.junit.jupiter.api.Test;

import static com.fundamentals.Tuples.tuple;
import static org.junit.jupiter.api.Assertions.*;

class Seq2ParserTest {

    @Test
    void parseValid() {
        final var hex = new LiteralParser("0x");
        final var num = new RegexParser("[0-9a-fA-F]+");
        final var parser = new Seq2Parser<>(hex, num);
        final var state = parser.parse("0xCAFEBABE");
        assertTrue(state.isPresent());
        assertEquals(tuple("0x", "CAFEBABE"), state.get());
    }

    @Test
    void parseInvalid() {
        final var hex = new LiteralParser("0x");
        final var num = new RegexParser("[0-9a-fA-F]+");
        final var parser = new Seq2Parser<>(hex, num);
        final var state = parser.parse("0x#");
        assertFalse(state.isPresent());
    }
}