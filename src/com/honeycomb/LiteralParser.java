package com.honeycomb;

import java.util.Optional;

/**
 * A {@link Parser} that accepts a literal string and succeeds if the
 * literal matches the input.
 */
final class LiteralParser implements Parser<String> {

    private final String literal;

    /**
     * Creates a new literal {@link Parser} accepting the string
     * {@code literal} or failing otherwise.
     *
     * @param literal the sting literal
     */
    public LiteralParser(String literal) {
        this.literal = literal;
    }

    @Override
    public Optional<State<? extends String>> parse(Cursor cur) {
        final var pos = cur.pos();
        final var val = cur.in();
        final var pln = literal.length();
        final var vln = val.length();
        if (pos < vln && val.substring(pos).startsWith(literal)) {
            return Optional.of(State.of(cur.advanceBy(pln), literal));
        }
        return Optional.empty();
    }
}
