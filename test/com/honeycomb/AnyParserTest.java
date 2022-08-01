package com.honeycomb;

import org.junit.jupiter.api.Test;

import static com.honeycomb.Assert.*;
import static com.honeycomb.Parsers.*;

class AnyParserTest {

    @Test
    void parseFirst() {
        final var p1 = literal("a");
        final var p2 = literal("b");
        final var p3 = literal("c");
        final var parser = any(p1, p2, p3);
        final var value = parser.parse("a");
        assertOptionalPresent("a", value);
    }

    @Test
    void parseSecond() {
        final var p1 = literal("a");
        final var p2 = literal("b");
        final var p3 = literal("c");
        final var parser = any(p1, p2, p3);
        final var value = parser.parse("b");
        assertOptionalPresent("b", value);
    }

    @Test
    void parseThird() {
        final var p1 = literal("a");
        final var p2 = literal("b");
        final var p3 = literal("c");
        final var parser = any(p1, p2, p3);
        final var value = parser.parse("c");
        assertOptionalPresent("c", value);
    }

    @Test
    void parseInvalid() {
        final var p1 = literal("a");
        final var p2 = literal("b");
        final var p3 = literal("c");
        final var parser = any(p1, p2, p3);
        final var value = parser.parse("d");
        assertOptionalEmpty(value);
    }

    @Test
    void parseMapFirst() {
        final var p1 = literal("a").map(ANode::new);
        final var p2 = literal("b").map(BNode::new);
        final var p3 = literal("c").map(CNode::new);
        final var parser = any(p1, p2, p3);
        final var value = parser.parse("a");
        assertOptionalPresent(new ANode("a"), value);
    }

    @Test
    void parseMapSecond() {
        final var p1 = literal("a").map(ANode::new);
        final var p2 = literal("b").map(BNode::new);
        final var p3 = literal("c").map(CNode::new);
        final var parser = any(p1, p2, p3);
        final var value = parser.parse("b");
        assertOptionalPresent(new BNode("b"), value);
    }

    @Test
    void parseMapThird() {
        final var p1 = literal("a").map(ANode::new);
        final var p2 = literal("b").map(BNode::new);
        final var p3 = literal("c").map(CNode::new);
        final var parser = any(p1, p2, p3);
        final var value = parser.parse("c");
        assertOptionalPresent(new CNode("c"), value);
    }

    interface Node {}

    record ANode(String val) implements Node { }
    record BNode(String val) implements Node { }
    record CNode(String val) implements Node { }
}