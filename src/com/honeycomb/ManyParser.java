package com.honeycomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<State<? extends List<T>>> parse(Cursor cur) {
        final var list = new ArrayList<T>();
        var state = parser.parse(cur);
        while (state.isPresent()) {
            list.add(state.get().val());
            cur = state.get().cur();
            state = parser.parse(cur);
        }
        return Optional.of(State.of(cur, list));
    }
}
