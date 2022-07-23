package com.honeycomb;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Prelude {

    public record Tuple<A, B>(A _1, B _2) {}

    public static <A, B> Tuple<A, B> tuple(A _1, B _2) { return new Tuple<>(_1, _2); }

//    @SafeVarargs
//    public static <T> List<T> append(List<T> es, T... ts) {
//        es.addAll(Arrays.asList(ts));
//        return es;
//    }

//    public static <T> List<T> prepend(T t, List<T> ts) {
//        ts.add(0, t);
//        return ts;
//    }

//    public static <T> List<T> prepend(List<T> ts, T t) {
//        ts.add(0, t);
//        return ts;
//    }
//
//    @SafeVarargs
//    public static <T> List<T> prependAll(List<T> es, T... ts) {
//        final var as = Arrays.asList(ts);
//        as.addAll(es);
//        return as;
//    }

//    public static <T> List<T> concat(List<T> es, List<T> fs) {
//        es.addAll(fs);
//        return es;
//    }

    public static <T, U> U reduce(T[] list, U identity, BiFunction<U, ? super T, U> accumulator) {
        return Arrays.stream(list).reduce(identity, accumulator, (_a, _b) -> null);
    }
}
