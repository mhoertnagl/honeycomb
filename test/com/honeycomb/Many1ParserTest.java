package com.honeycomb;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.honeycomb.Assert.*;
import static com.honeycomb.Parsers.*;

class Many1ParserTest {

    @Test
    void parseZeroTimes() {
        final var parser = many1(literal("a"));
        final var value = parser.parse("");
        assertError(value);
    }

    @Test
    void parseOnce() {
        final var parser = many1(literal("a"));
        final var value = parser.parse("a");
        assertValid(List.of("a"), value);
    }

    @Test
    void parseTwice() {
        final var parser = many1(literal("a"));
        final var value = parser.parse("aa");
        assertValid(List.of("a", "a"), value);
    }

    @Test
    void parseMap() {
        record Node(String val) {}
        final var expected = List.of(
                new Node("a"),
                new Node("a"),
                new Node("a"),
                new Node("a")
        );
        final var parser = many1(literal("a").map(Node::new));
        final var value = parser.parse("aaaa");
        assertValid(expected, value);
    }
}