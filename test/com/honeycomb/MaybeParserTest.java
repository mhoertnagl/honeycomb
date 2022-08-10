package com.honeycomb;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.honeycomb.Assert.*;
import static com.honeycomb.Parsers.*;

class MaybeParserTest {

    @Test
    void parseExisting() {
        final var parser = maybe(literal("private"));
        final var value = parser.parse("private");
        assertValid(Optional.of("private"), value);
    }

    @Test
    void parseNonExisting() {
        final var parser = maybe(literal("private"));
        final var value = parser.parse("");
        assertValid(Optional.empty(), value);
    }

    @Test
    void parseExistingAndMap() {
        record MethodNode(boolean isPrivate) {}
        final var parser = maybe(literal("private"))
                .map(Optional::isPresent)
                .map(MethodNode::new);
        final var value = parser.parse("private");
        assertValid(new MethodNode(true), value);
    }

    @Test
    void parseNonExistingAndMap() {
        record MethodNode(boolean isPrivate) {}
        final var parser = maybe(literal("private"))
                .map(Optional::isPresent)
                .map(MethodNode::new);
        final var value = parser.parse("");
        assertValid(new MethodNode(false), value);
    }
}