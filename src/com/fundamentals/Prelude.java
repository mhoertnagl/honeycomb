package com.fundamentals;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class Prelude {

    /**
     * A {@link BinaryOperator} that always returns {@code null}.
     *
     * @return a binary operator that always returns {@code null}.
     * @param <T> the type of the operands and result of the operator
     */
    public static <T> BinaryOperator<T> nullBinOp() {
        return (_a, _b) -> null;
    }

    public static <T> List<T> list() {
        return Collections.emptyList();
    }

    public static <T> List<T> list(T e) {
        return Collections.singletonList(e);
    }

    @SafeVarargs
    public static <T> List<T> list(T... es) {
        return List.of(es);
    }

    public static <T> Optional<T> head(List<T> es) {
        return Optional.ofNullable(head(es, null));
    }

    public static <T> T head(List<T> es, T defaultValue) {
        return es.isEmpty() ? defaultValue : es.get(0);
    }

    public static <T> List<T> tail(List<T> es) {
        return es.isEmpty() ? list() : es.subList(1, es.size());
    }

    public static <T> Optional<T> last(List<T> es) {
        return Optional.ofNullable(last(es, null));
    }

    public static <T> T last(List<T> es, T defaultValue) {
        return es.isEmpty() ? defaultValue : es.get(es.size()-1);
    }

    public static <T> List<T> init(List<T> es) {
        return es.isEmpty() ? list() : es.subList(0, es.size()-1);
    }

    public static <T> List<T> reverse(List<T> es) {
        final var as = new ArrayList<T>(es.size());
        for (var i = es.size()-1; i >= 0; i--) {
            as.add(es.get(i));
        }
        return Collections.unmodifiableList(as);
    }

    @SafeVarargs
    public static <T> List<T> append(List<T> es, T... ts) {
        final var as = new ArrayList<>(es);
        as.addAll(Arrays.asList(ts));
        return Collections.unmodifiableList(as);
        //return List.copyOf(as);
    }

    @SafeVarargs
    public static <T> List<T> appendMut(List<T> es, T... ts) {
        es.addAll(Arrays.asList(ts));
        return es;
    }

    @SafeVarargs
    public static <T> List<T> prepend(List<T> es, T... ts) {
        final var as = Arrays.asList(ts);
        as.addAll(es);
        return Collections.unmodifiableList(as);
        //return List.copyOf(as);
    }

    @SafeVarargs
    public static <T> List<T> prependMut(List<T> es, T... ts) {
        final var as = Arrays.asList(ts);
        as.addAll(es);
        return as;
    }

    public static <T> List<T> concat(List<T> es, List<T> fs) {
        final var as = new ArrayList<>(es);
        as.addAll(fs);
        return Collections.unmodifiableList(as);
    }

    public static <T> List<T> concatMut(List<T> es, List<T> fs) {
        es.addAll(fs);
        return es;
    }

    public static <T, U> U foldLeft(List<T> es, U identity, BiFunction<U, T, U> f) {
        var result = identity;
        for (var e : es) {
            result = f.apply(result, e);
        }
        return result;
    }

    public static <T, U> U foldRight(List<T> es, U identity, BiFunction<U, T, U> f) {
        var result = identity;
        for (var i = es.size()-1; i >= 0; i--) {
            result = f.apply(result, es.get(i));
        }
        return result;
    }
}
