package com.honeycomb;

import org.junit.jupiter.api.Test;

import static com.honeycomb.Assert.*;
import static com.honeycomb.Parsers.*;

class LiteralParserTest {

    @Test
    void parseValid() {
        final var parser = literal("class");
        final var value = parser.parse("class");
        assertValid("class", value);
    }

    @Test
    void parseInvalid() {
        final var parser = literal("test");
        final var value = parser.parse("Test");
        assertError(value, "hui");
    }

    @Test
    void parseAndMap() {
        record IdNode(String id) {}
        final var parser = literal("if").map(IdNode::new);
        final var value = parser.parse("if");
        assertValid(new IdNode("if"), value);
    }
}