package com.honeycomb;

import org.junit.jupiter.api.Test;

import static com.honeycomb.Assert.*;
import static com.honeycomb.Parsers.regex;

class RegexParserTest {

    private static final String ID = "[a-zA-Z][a-zA-Z0-9]*";

    @Test
    void parseValid() {
        final var parser = regex(ID);
        final var value = parser.parse("value0");
        assertOptionalPresent("value0", value);
    }

    @Test
    void parseInvalid() {
        final var parser = regex(ID);
        final var value = parser.parse("42");
        assertOptionalEmpty(value);
    }

    @Test
    void parseAndMap() {
        record IdNode(String id) {}
        final var parser = regex(ID).map(IdNode::new);
        final var value = parser.parse("variable42");
        assertOptionalPresent(new IdNode("variable42"), value);
    }
}