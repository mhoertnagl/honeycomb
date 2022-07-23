package com.honeycomb;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.honeycomb.Prelude.*;
import static com.honeycomb.Parsers.*;

/**
 *
 *
 * @param <T> the type of the parser result value.
 * @since 1.0
 */
public interface Parser<T> {

    /**
     * Holds the input string {@code in} and the current character position
     * {@code pos} in the input string.
     *
     * @param in the input string to be parsed
     * @param pos the character position in the input string
     */
    record Cursor(String in, Integer pos) {

        /**
         * Advances the {@link Cursor} position by {@code offset} bytes.
         *
         * @param offset the number of bytes to advance the cursor's position
         * @return a new {@link Cursor} offset by specified number of bytes.
         */
        public Cursor advanceBy(Integer offset) {
            return new Cursor(in, pos + offset);
        }

        /**
         * Positions the {@link Cursor} at the new position {@code newPos}.
         *
         * @param newPos the new position of the cursor
         * @return a new {@link Cursor} at the new position
         */
        public Cursor positionAt(Integer newPos) {
            return new Cursor(in, newPos);
        }
    }

    record State<T>(Cursor cur, T val) {

        public static <U> State<U> of(Cursor cur, U val) {
            return new State<>(cur, val);
        }

        public <U> State<U> map(Function<T, U> mapping) {
            return State.of(cur, mapping.apply(val));
        }
    }

    /**
     * Parses the input at the current {@link Cursor} position and yields the
     * new {@link State} containing the new {@link Cursor} position and the
     * parsing result.
     *
     * @param cur the {@link Cursor} position before this parser is invoked
     * @return the {@link State} before this parser is invoked
     */
    Optional<State<T>> parse(Cursor cur);

    default Optional<T> parse(String in) {
        return parse(new Cursor(in, 0)).map(s -> s.val);
    }

    default Parser<Tuple<T, String>> then(String pattern) {
        return cur -> then(literal(pattern)).parse(cur);
    }

    default <U> Parser<Tuple<T, U>> then(Supplier<Parser<U>> that) {
        return cur -> then(that.get()).parse(cur);
    }

    // TODO: one-liner?
    default <U> Parser<Tuple<T, U>> then(Parser<U> that) {
        return cur -> parse(cur)
                .flatMap(t -> that.parse(t.cur)
                        .map(u -> State.of(u.cur, tuple(t.val, u.val))));
    }

    default Parser<String> skipLeft(String pattern) {
        return cur -> skipLeft(literal(pattern)).parse(cur);
    }

    default <U> Parser<U> skipLeft(Supplier<Parser<U>> that) {
        return cur -> skipLeft(that.get()).parse(cur);
    }

    default <U> Parser<U> skipLeft(Parser<U> that) {
        return cur -> then(that).parse(cur).map(s -> s.map(Tuple::_2));
    }

    default Parser<T> skipRight(String pattern) {
        return cur -> skipRight(literal(pattern)).parse(cur);
    }

    default Parser<T> skipRight(Supplier<Parser<?>> that) {
        return cur -> skipRight(that.get()).parse(cur);
    }

    default Parser<T> skipRight(Parser<?> that) {
        return cur -> then(that).parse(cur).map(s -> s.map(Tuple::_1));
    }

    default Parser<T> or(Supplier<Parser<T>> that) {
        return cur -> or(that.get()).parse(cur);
    }

    default Parser<T> or(Parser<T> that) {
        return cur -> parse(cur).or(() -> that.parse(cur));
    }

    default <U> Parser<U> map(Function<T, U> mapping) {
        return cur -> parse(cur).map(s -> s.map(mapping));
    }
}
