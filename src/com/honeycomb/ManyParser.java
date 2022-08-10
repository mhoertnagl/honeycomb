package com.honeycomb;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Parser} that accepts another {@link Parser} {@code parser}
 * and invokes {@code parser} as long as there is a match.
 * The {@link Parser} will always succeed, even if {@code parser} does
 * not match even once.
 *
 * @param <T> the type of the parsed result value
 */
public final class ManyParser<T> implements Parser<List<T>> {

    private final Parser<T> parser;

    /**
     * Creates a new {@link Parser} that applies {@link Parser}
     * {@code parser} zero or more times.
     *
     * @param parser the {@link Parser} to apply zero or more times
     */
    public ManyParser(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    public State<? extends List<T>> parse(Cursor cur) {
        final var list = new ArrayList<T>();
        var state = parser.parse(cur);
        while (state.isValid()) {
            list.add(state.val().orElse(null));
            cur = state.cur().orElse(null);
            state = parser.parse(cur);
        }
        return State.of(cur, list);
    }
}
