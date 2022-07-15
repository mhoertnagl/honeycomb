package com.honeycomb.parsers;

import org.junit.jupiter.api.Test;

import static com.honeycomb.tuples.Tuples.tuple;
import static org.junit.jupiter.api.Assertions.*;

class Seq3ParserTest {

    @Test
    void parseValid() {
        final var def = new LiteralParser("def");
        final var WS = new RegexParser("\s+");
        final var id = new RegexParser("[a-zA-Z][a-zA-Z0-9]*");
        final var parser = new Seq3Parser<>(def, WS, id);
        final var state = parser.parse("def map");
        assertTrue(state.isPresent());
        assertEquals(tuple("def", " ", "map"), state.get());
    }
}