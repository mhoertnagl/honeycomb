package com.honeycomb;

import java.util.List;
import java.util.function.BiFunction;

/**
 * A functional prelude.
 *
 * @since 1.0
 */
public final class Prelude {

    @FunctionalInterface
    public interface Fun2<T1, T2, R> {
        R apply(T1 t1, T2 t2);
    }

    @FunctionalInterface
    public interface Fun3<T1, T2, T3, R> {
        R apply(T1 t1, T2 t2, T3 t3);
    }

    @FunctionalInterface
    public interface Fun4<T1, T2, T3, T4, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    @FunctionalInterface
    public interface Fun5<T1, T2, T3, T4, T5, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    @FunctionalInterface
    public interface Fun6<T1, T2, T3, T4, T5, T6, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
    }

    @FunctionalInterface
    public interface Fun7<T1, T2, T3, T4, T5, T6, T7, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
    }

    @FunctionalInterface
    public interface Fun8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
    }

    public record Tuple<T1, T2>(T1 _1, T2 _2) {
    }

    public static <T1, T2> Tuple<T1, T2> tuple(T1 _1, T2 _2) {
        return new Tuple<>(_1, _2);
    }

//    @SafeVarargs
//    public static <T> List<T> append(List<T> es, T... ts) {
//        es.addAll(Arrays.asList(ts));
//        return es;
//    }

    /**
     * Prepends an element {@code t} to the list {@code ts} and returns the
     * mutated list.
     *
     * @param <T> the type of the list as well as the element
     * @param t   the element to prepend
     * @param ts  the list to prepend the element {@code t} to
     * @return the same list with the element {@code t} prepended
     */
    public static <T> List<T> prepend(T t, List<T> ts) {
        ts.add(0, t);
        return ts;
    }

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

    /**
     * Performs a reduction on the elements of this array, using the provided
     * identity and accumulation functions. It is constraint to execute
     * sequentially.
     *
     * @param <T> the type of the elements
     * @param <U> the type of the result
     * @param array the arrray of elements to reduce
     * @param identity the identity value for the combiner function
     * @param accumulator a function for incorporating an additional element
     *                    into a result
     * @return the result of the reduction
     */
    public static <T, U> U reduce(T[] array, U identity, BiFunction<U, ? super T, U> accumulator) {
        U result = identity;
        for (T element : array) {
            result = accumulator.apply(result, element);
        }
        return result;
    }
}
