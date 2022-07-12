package com.honeycomb.parsers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnyParserTest {

    @Test
    void parseFirst() {
        final var p1 = new LiteralParser("a");
        final var p2 = new LiteralParser("b");
        final var p3 = new LiteralParser("c");
        final var parser = new AnyParser<>(p1, p2, p3);
        final var state = parser.parse("a");
        assertTrue(state.isPresent());
        assertEquals("a", state.get());
    }

    @Test
    void parseSecond() {
        final var p1 = new LiteralParser("a");
        final var p2 = new LiteralParser("b");
        final var p3 = new LiteralParser("c");
        final var parser = new AnyParser<>(p1, p2, p3);
        final var state = parser.parse("b");
        assertTrue(state.isPresent());
        assertEquals("b", state.get());
    }

    @Test
    void parseThird() {
        final var p1 = new LiteralParser("a");
        final var p2 = new LiteralParser("b");
        final var p3 = new LiteralParser("c");
        final var parser = new AnyParser<>(p1, p2, p3);
        final var state = parser.parse("c");
        assertTrue(state.isPresent());
        assertEquals("c", state.get());
    }

    @Test
    void parseInvalid() {
        final var p1 = new LiteralParser("a");
        final var p2 = new LiteralParser("b");
        final var p3 = new LiteralParser("c");
        final var parser = new AnyParser<>(p1, p2, p3);
        final var state = parser.parse("d");
        assertFalse(state.isPresent());
    }

    @Test
    void parseMapFirst() {
        record ANode(String val) {}
        record BNode(String val) {}
        record CNode(String val) {}
        final var p1 = new LiteralParser("a").map(ANode::new);
        final var p2 = new LiteralParser("b").map(BNode::new);
        final var p3 = new LiteralParser("c").map(CNode::new);
        final var parser = new AnyParser<>(p1, p2, p3);
        final var state = parser.parse("a");
        assertTrue(state.isPresent());
        assertEquals(new ANode("a"), state.get());
    }

    @Test
    void parseMapSecond() {
        record ANode(String val) {}
        record BNode(String val) {}
        record CNode(String val) {}
        final var p1 = new LiteralParser("a").map(ANode::new);
        final var p2 = new LiteralParser("b").map(BNode::new);
        final var p3 = new LiteralParser("c").map(CNode::new);
        final var parser = new AnyParser<>(p1, p2, p3);
        final var state = parser.parse("b");
        assertTrue(state.isPresent());
        assertEquals(new BNode("b"), state.get());
    }

    @Test
    void parseMapThird() {
        record ANode(String val) {}
        record BNode(String val) {}
        record CNode(String val) {}
        final var p1 = new LiteralParser("a").map(ANode::new);
        final var p2 = new LiteralParser("b").map(BNode::new);
        final var p3 = new LiteralParser("c").map(CNode::new);
        final var parser = new AnyParser<>(p1, p2, p3);
        final var state = parser.parse("c");
        assertTrue(state.isPresent());
        assertEquals(new CNode("c"), state.get());
    }
}