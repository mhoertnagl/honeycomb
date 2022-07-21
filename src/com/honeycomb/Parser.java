package com.honeycomb;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.honeycomb.Prelude.*;
import static com.honeycomb.Parsers.*;

public interface Parser<T> {

    record Cursor(String in, Integer pos) {}

    record Result<T>(Cursor cur, T val) {

        public <U> Result<U> map(Function<T, U> mapping) {
            return new Result<>(cur, mapping.apply(val));
        }
    }

    Optional<Result<T>> parse(Cursor in);

    default Optional<T> parse(String in) {
        return parse(new Cursor(in, 0)).map(r -> r.val);
    }

    default Parser<Tuple<T, String>> then(String pattern) {
        return in -> then(literal(pattern)).parse(in);
    }

    default <U> Parser<Tuple<T, U>> then(Supplier<Parser<U>> that) {
        return in -> then(that.get()).parse(in);
    }

    // TODO: one-liner?
    default <U> Parser<Tuple<T, U>> then(Parser<U> that) {
        return in -> parse(in)
                .flatMap(rt -> that.parse(rt.cur)
                        .map(ru -> new Result<>(ru.cur, new Tuple<>(rt.val, ru.val))));
    }

    default Parser<String> skipLeft(String pattern) {
        return in -> skipLeft(literal(pattern)).parse(in);
    }

    default <U> Parser<U> skipLeft(Supplier<Parser<U>> that) {
        return in -> skipLeft(that.get()).parse(in);
    }

    default <U> Parser<U> skipLeft(Parser<U> that) {
        return in -> then(that).parse(in).map(r -> r.map(Tuple::_2));
    }

    default Parser<T> skipRight(String pattern) {
        return in -> skipRight(literal(pattern)).parse(in);
    }

    default Parser<T> skipRight(Supplier<Parser<?>> that) {
        return in -> skipRight(that.get()).parse(in);
    }

    default Parser<T> skipRight(Parser<?> that) {
        return in -> then(that).parse(in).map(r -> r.map(Tuple::_1));
    }

    default Parser<T> or(Supplier<Parser<T>> that) {
        return in -> or(that.get()).parse(in);
    }

    default Parser<T> or(Parser<T> that) {
        return in -> parse(in).or(() -> that.parse(in));
    }

    default <U> Parser<U> map(Function<T, U> mapping) {
        return in -> parse(in).map(r -> r.map(mapping));
    }
}
