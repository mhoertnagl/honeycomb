package com.honeycomb;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MaybeParserTest {

//    @Test
//    void parseExisting() {
//        final var p = new RegexParser("[a-zA-Z][a-zA-Z0-9]*");
//        final var parser = new MaybeParser<>(p);
//        final var state = parser.parse("prevVal");
//        assertTrue(state.isPresent());
//        assertEquals(Optional.of("prevVal"), state.get());
//    }
//
//    @Test
//    void parseNonExisting() {
//        final var p = new RegexParser("[a-zA-Z][a-zA-Z0-9]*");
//        final var parser = new MaybeParser<>(p);
//        final var state = parser.parse("42");
//        assertTrue(state.isPresent());
//        assertEquals(Optional.empty(), state.get());
//    }
//
//    @Test
//    void parseMap() {
//        record IdNode(String id) {}
//        final var p = new RegexParser("[a-zA-Z][a-zA-Z0-9]*")
//                .map(IdNode::new);
//        final var parser = new MaybeParser<>(p);
//        final var state = parser.parse("prevVal");
//        assertTrue(state.isPresent());
//        assertEquals(Optional.of(new IdNode("prevVal")), state.get());
//    }

    // TODO: Test sequence with optional value.
}