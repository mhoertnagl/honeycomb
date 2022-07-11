package com.honeycomb.parsers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LiteralParserTest {

    @Test
    void parseValid() {
        final var parser = new LiteralParser("test");
        final var state = parser.parse("test");
        assertTrue(state.isPresent());
        assertEquals("test", state.get());
    }

    @Test
    void parseInvalid() {
        final var parser = new LiteralParser("test");
        final var state = parser.parse("Test");
        assertFalse(state.isPresent());
    }

    @Test
    void parseAndMap() {
        record IdNode(String id) {}
        final var parser = new LiteralParser("test").map(IdNode::new);
        final var state = parser.parse("test");
        assertTrue(state.isPresent());
        assertEquals(new IdNode("test"), state.get());
    }
}