package com.fundamentals;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.fundamentals.Prelude.*;

public final class Optionals {

    public static <T> Optional<T> or(Optional<T> a, Optional<T> b) {
        return a.or(() -> b);
    }

    public static <T> Optional<List<T>> all(List<Optional<T>> es) {
        final var s = es
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return s.size() == es.size() ? Optional.of(s) : Optional.empty();
    }

    public static <T> Optional<T> any(List<Optional<T>> es) {
        return es.stream().reduce(Optional.empty(), Optionals::or, nullBinOp());
    }

    public static <A,B> Function<Optional<A>, Optional<B>> lift(Function<A, B> f) {
        return a -> a.map(f);
    }
}
