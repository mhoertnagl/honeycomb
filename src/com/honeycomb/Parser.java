package com.honeycomb;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.honeycomb.Prelude.*;
import static com.honeycomb.Parsers.*;

public interface Parser<T> {

    record Cursor(String in, Integer pos) {

        public Cursor advanceBy(Integer offset) {
            return new Cursor(in, pos + offset);
        }

        public Cursor positionAt(Integer newPos) {
            return new Cursor(in, newPos);
        }
    }

    record Result<T>(Cursor cur, T val) {

        public static <U> Result<U> of(Cursor cur, U val) {
            return new Result<>(cur, val);
        }

        public <U> Result<U> map(Function<T, U> mapping) {
            return Result.of(cur, mapping.apply(val));
        }
    }

    Optional<Result<T>> parse(Cursor cur);

    default Optional<T> parse(String in) {
        return parse(new Cursor(in, 0)).map(r -> r.val);
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
                .flatMap(rt -> that.parse(rt.cur)
                        .map(ru -> Result.of(ru.cur, tuple(rt.val, ru.val))));
    }

    default Parser<String> skipLeft(String pattern) {
        return cur -> skipLeft(literal(pattern)).parse(cur);
    }

    default <U> Parser<U> skipLeft(Supplier<Parser<U>> that) {
        return cur -> skipLeft(that.get()).parse(cur);
    }

    default <U> Parser<U> skipLeft(Parser<U> that) {
        return cur -> then(that).parse(cur).map(r -> r.map(Tuple::_2));
    }

    default Parser<T> skipRight(String pattern) {
        return cur -> skipRight(literal(pattern)).parse(cur);
    }

    default Parser<T> skipRight(Supplier<Parser<?>> that) {
        return cur -> skipRight(that.get()).parse(cur);
    }

    default Parser<T> skipRight(Parser<?> that) {
        return cur -> then(that).parse(cur).map(r -> r.map(Tuple::_1));
    }

    default Parser<T> or(Supplier<Parser<T>> that) {
        return cur -> or(that.get()).parse(cur);
    }

    default Parser<T> or(Parser<T> that) {
        return cur -> parse(cur).or(() -> that.parse(cur));
    }

    default <U> Parser<U> map(Function<T, U> mapping) {
        return cur -> parse(cur).map(r -> r.map(mapping));
    }
}
