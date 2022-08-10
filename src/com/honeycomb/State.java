package com.honeycomb;

import java.util.function.Function;

/**
 * State of the parser. It holds the input {@link Cursor} and the parsed
 * result value, usually some sort of AST structure.
 *
 * @param cur the input {@link Cursor}
 * @param val the parser result value
 * @param <T> the type of the parsed result value
 */
public record State<T>(Cursor cur, T val) {

    /**
     * Creates a new parser {@link State} from an input {@link Cursor}
     * and a result value.
     *
     * @param cur the input {@link Cursor}
     * @param val the result value
     * @return a new parser {@link State}
     * @param <U> the type of the parsed result value
     */
    public static <U> State<U> of(Cursor cur, U val) {
        return new State<>(cur, val);
    }

    public <U> State<U> flatMap(Function<? super T, State<U>> mapping) {
        return mapping.apply(val);
    }

    /**
     * Maps the result value of this parser {@link State} to a new state.
     *
     * @param mapping the result value mapping
     * @return a new parser {@link State} with te mapped result value
     * @param <U> the type of the mapped result value
     */
    public <U> State<U> map(Function<? super T, ? extends U> mapping) {
        return State.of(cur, mapping.apply(val));
    }
}