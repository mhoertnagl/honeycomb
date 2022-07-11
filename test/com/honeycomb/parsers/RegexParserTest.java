package com.honeycomb.parsers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexParserTest {

    @Test
    void parseValid() {
        final var parser = new RegexParser("[a-zA-Z][a-zA-Z0-9]*");
        final var state = parser.parse("value0");
        assertTrue(state.isPresent());
        assertEquals("value0", state.get());
    }

    @Test
    void parseInvalid() {
        final var parser = new RegexParser("[a-zA-Z][a-zA-Z0-9]*");
        final var state = parser.parse("42");
        assertFalse(state.isPresent());
    }

    @Test
    void parseAndMap() {
        record IdNode(String id) {}
        final var parser = new RegexParser("[a-zA-Z][a-zA-Z0-9]*")
                .map(IdNode::new);
        final var state = parser.parse("Property23");
        assertTrue(state.isPresent());
        assertEquals(new IdNode("Property23"), state.get());
    }
}