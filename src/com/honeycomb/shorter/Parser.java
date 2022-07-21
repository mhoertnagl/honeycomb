package com.honeycomb.shorter;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Parser<T> {

    record Cursor(String in, Integer pos) {}

    record Result<T>(Cursor cur, T val) {}

    Optional<Result<T>> parse(Cursor in);

    default Optional<T> parse(String in) {
        return parse(new Cursor(in, 0)).map(r -> r.val);
    }

    default Parser<Pair<T, String>> then(String pattern) {
        return then(Parsers.literal(pattern));
    }

    default <U> Parser<Pair<T, U>> then(Supplier<Parser<U>> that) {
        return in -> then(that.get()).parse(in);
    }

    default <U> Parser<Pair<T, U>> then(Parser<U> that) {
        return in -> parse(in)
                .flatMap(rt -> that.parse(rt.cur)
                        .map(ru -> new Result<>(ru.cur, new Pair<>(rt.val, ru.val))));
    }

    default <U> Parser<U> skip(Parser<U> that) {
        return in -> parse(in).flatMap(rt -> that.parse(rt.cur));
    }

    default Parser<T> or(Supplier<Parser<T>> that) {
        return in -> or(that.get()).parse(in);
        // return in -> parse(in).or(() -> that.get().parse(in));
    }

    default Parser<T> or(Parser<T> that) {
        return in -> parse(in).or(() -> that.parse(in));
    }

    default <U> Parser<U> map(Function<T, U> mapping) {
        return in -> parse(in).map(r -> new Result<>(r.cur, mapping.apply(r.val)));
    }
}
