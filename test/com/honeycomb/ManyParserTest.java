package com.honeycomb;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManyParserTest {

//    @Test
//    void parseZeroTimes() {
//        final var p = new LiteralParser("a");
//        final var parser = new ManyParser<>(p);
//        final var state = parser.parse("");
//        assertTrue(state.isPresent());
//        assertEquals(List.of(), state.get());
//    }
//
//    @Test
//    void parseOnce() {
//        final var p = new LiteralParser("a");
//        final var parser = new ManyParser<>(p);
//        final var state = parser.parse("a");
//        assertTrue(state.isPresent());
//        assertEquals(List.of("a"), state.get());
//    }
//
//    @Test
//    void parseTwice() {
//        final var p = new LiteralParser("a");
//        final var parser = new ManyParser<>(p);
//        final var state = parser.parse("aa");
//        assertTrue(state.isPresent());
//        assertEquals(List.of("a", "a"), state.get());
//    }
//
//    @Test
//    void parseMap() {
//        record Node(String val) {}
//        final var expected = List.of(
//                new Node("a"),
//                new Node("a"),
//                new Node("a"),
//                new Node("a")
//        );
//        final var p = new LiteralParser("a").map(Node::new);
//        final var parser = new ManyParser<>(p);
//        final var state = parser.parse("aaaa");
//        assertTrue(state.isPresent());
//        assertEquals(expected, state.get());
//    }
}