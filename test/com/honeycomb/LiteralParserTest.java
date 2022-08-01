package com.honeycomb;

import org.junit.jupiter.api.Test;

import static com.honeycomb.Assert.*;
import static com.honeycomb.Parsers.*;

class LiteralParserTest {

    @Test
    void parseValid() {
        final var parser = literal("class");
        final var value = parser.parse("class");
        assertOptionalPresent("class", value);
    }

    @Test
    void parseInvalid() {
        final var parser = literal("test");
        final var value = parser.parse("Test");
        assertOptionalEmpty(value);
    }

    @Test
    void parseAndMap() {
        record IdNode(String id) {}
        final var parser = literal("if").map(IdNode::new);
        final var value = parser.parse("if");
        assertOptionalPresent(new IdNode("if"), value);
    }
}