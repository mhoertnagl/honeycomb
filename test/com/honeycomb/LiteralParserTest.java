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
        assertError(value, new State.ErrorMessage(1, 1, "'test' expected"));
    }

    @Test
    void parseInvalid2() {
        final var parser = literal("test").then(literal("this"));
        final var value = parser.parse("testthat");
        assertError(value, new State.ErrorMessage(1, 5, "'this' expected"));
    }

    @Test
    void parseAndMap() {
        record IdNode(String id) {}
        final var parser = literal("if").map(IdNode::new);
        final var value = parser.parse("if");
        assertValid(new IdNode("if"), value);
    }
}