package com.honeycomb;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.honeycomb.Prelude.*;
import static com.honeycomb.Parsers.*;

/**
 * Defines a Honeycomb parser.
 *
 * @param <T> the type of the parser result value
 * @since 1.0
 */
public interface Parser<T> {

    /**
     * Parses the input at the current {@link Cursor} position and yields the
     * new {@link State} containing the new {@link Cursor} position and the
     * parsing result.
     *
     * @param cur the {@link Cursor} position before this parser is invoked
     * @return the {@link State} before this parser is invoked
     */
    State<? extends T> parse(Cursor cur);

    /**
     * Parses the input string starting at the beginning and returns an
     * {@link Optional} containing the parsed result value.
     *
     * @param in the input string
     * @return an {@link Optional} containing the parsed result value
     */
    default State<? extends T> parse(String in) {
        return parse(new Cursor(in, 0)); //.map(s -> s.val());
    }

    /**
     * Attempts this {@link Parser} and, if successful, continues with a
     * {@link Parser} parsing {@code literal}. Returns a {@link Tuple}
     * containing both parsed values.
     *
     * @param literal the literal string
     * @return a {@link Parser} invoking two successive {@link Parser}s,
     *         the second of them a literal {@link Parser}.
     */
    default Parser<Tuple<T, String>> then(String literal) {
        return then(literal(literal));
    }

    /**
     * Attempts this {@link Parser} and, if successful, calls the
     * {@link Supplier} and continues with {@code that} {@link Parser}.
     * Returns a {@link Tuple} containing both parsed values.
     *
     * @param that the other {@link Parser}
     * @return a {@link Parser} invoking two successive {@link Parser}s
     * @param <U> the type of {@code that} parser result value
     */
    default <U> Parser<Tuple<T, U>> then(Supplier<Parser<U>> that) {
        return cur -> then(that.get()).parse(cur);
    }

    /**
     * Attempts this {@link Parser} and, if successful, continues with
     * {@code that} {@link Parser}. Returns a {@link Tuple} containing both
     * parsed values.
     *
     * @param that the other {@link Parser}
     * @return a {@link Parser} invoking two successive {@link Parser}s
     * @param <U> the type of {@code that} parser result value
     */
    default <U> Parser<Tuple<T, U>> then(Parser<U> that) {
        return cur -> parse(cur)
                .flatMap((ct, t) -> that.parse(ct)
                .flatMap((cu, u) -> State.of(cu, tuple(t, u))));
    }

    /**
     * Attempts this {@link Parser}, then attempts to parse the string
     * {@code pattern} but ignores the this {@link Parser}s result and
     * returns the matched string {@code pattern} only.
     *
     * @param pattern a string literal
     * @return a {@link Parser} that only returns the right result.
     */
    default Parser<String> skipLeft(String pattern) {
        return skipLeft(literal(pattern));
    }

    /**
     * Calls the {@link Supplier} and attempts this {@link Parser}, then
     * attempts to parse {@code that} @link Parser} but ignores the this
     * {@link Parser}s result and returns {@code that} {@link Parser}s
     * matched result only.
     *
     * @param that a string literal
     * @return a {@link Parser} that only returns the right result.
     */
    default <U> Parser<U> skipLeft(Supplier<Parser<U>> that) {
        return cur -> skipLeft(that.get()).parse(cur);
    }

    /**
     * Attempts this {@link Parser}, then attempts to parse {@code that}
     * {@link Parser} but ignores the this {@link Parser}s result and
     * returns {@code that} {@link Parser}s matched result only.
     *
     * @param that a string literal
     * @return a {@link Parser} that only returns the right result.
     */
    default <U> Parser<U> skipLeft(Parser<U> that) {
        return then(that).map(Tuple::_2);
    }

    /**
     * Attempts this {@link Parser}, then attempts to parse the string
     * {@code pattern} but ignores the parsed {@code pattern} and returns
     * the parsed result of this {@link Parser} only.
     *
     * @param pattern a string literal
     * @return a {@link Parser} that only returns the left result.
     */
    default Parser<T> skipRight(String pattern) {
        return skipRight(literal(pattern));
    }

    /**
     * Calls the {@link Supplier} and attempts this {@link Parser}, then
     * {@code that} but ignores the parsed result of {@code that}
     * {@link Parser} and returns the parsed result of this
     * {@link Parser} only.
     *
     * @param that the next {@link Parser}
     * @return a {@link Parser} that only returns the left result.
     */
    default Parser<T> skipRight(Supplier<Parser<?>> that) {
        return cur -> skipRight(that.get()).parse(cur);
    }

    /**
     * Attempts this {@link Parser}, then {@code that} but ignores the parsed
     * result of {@code that} {@link Parser} and returns the parsed result of
     * this {@link Parser} only.
     *
     * @param that the next {@link Parser}
     * @return a {@link Parser} that only returns the left result.
     */
    default Parser<T> skipRight(Parser<?> that) {
        return then(that).map(Tuple::_1);
    }

    /**
     * Attempts this {@link Parser} and, if unsuccessful, calls the
     * {@link Supplier} and continues with {@code that} {@link Parser}.
     * Returns the result of the first matching {@link Parser}.
     *
     * @param that the alternative {@link Parser}
     * @return a {@link Parser} invoking this or {@code that} {@link Parser}
     */
    default Parser<T> or(Supplier<Parser<? extends T>> that) {
        return cur -> or(that.get()).parse(cur);
    }

    /**
     * Attempts this {@link Parser} and, if unsuccessful, continues with
     * {@code that} {@link Parser}. Returns the result of the first matching
     * {@link Parser}.
     *
     * @param that the alternative {@link Parser}
     * @return a {@link Parser} invoking this or {@code that} {@link Parser}
     */
    default Parser<T> or(Parser<? extends T> that) {
        return new OrParser<>(this, that);
//        return cur -> {
//            final var s = parse(cur);
//            if (s.isValid()) {
//                return s;
//            }
//            return that.parse(cur);
//        };
        // return cur -> parse(cur).or(() -> that.parse(cur));
    }

    /**
     * Maps the result of this {@link Parser} to a value of type {@code U}.
     *
     * @param mapping the result value mapping
     * @return the same {@link Parser} but with the result mapped to type
     *         {@code U}.
     * @param <U> the type of the mapped value
     */
    default <U> Parser<U> map(Function<? super T, ? extends U> mapping) {
        return cur -> parse(cur).map(mapping);
    }
}
