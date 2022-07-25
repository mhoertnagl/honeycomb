package com.honeycomb;

import java.util.function.Function;

import static com.honeycomb.Prelude.*;

/**
 * @since 1.0
 */
public class Conversions {

    public static <T1, R>
    Function<T1, R>
    to(Function<T1, R> f) {
        return f;
    }

    public static <T1, T2, R>
    Function<Tuple<T1, T2>, R>
    to(Fun2<T1, T2, R> f) {
        return p -> {
            final var t1 = p._1();
            final var t2 = p._2();
            return f.apply(t1, t2);
        };
    }

    public static <T1, T2, T3, R>
    Function<Tuple<Tuple<T1, T2>, T3>, R>
    to(Fun3<T1, T2, T3, R> f) {
        return p -> {
            final var t1 = p._1()._1();
            final var t2 = p._1()._2();
            final var t3 = p._2();
            return f.apply(t1, t2, t3);
        };
    }

    public static <T1, T2, T3, T4, R>
    Function<Tuple<Tuple<Tuple<T1, T2>, T3>, T4>, R>
    to(Fun4<T1, T2, T3, T4, R> f) {
        return p -> {
            final var t1 = p._1()._1()._1();
            final var t2 = p._1()._1()._2();
            final var t3 = p._1()._2();
            final var t4 = p._2();
            return f.apply(t1, t2, t3, t4);
        };
    }

    public static <T1, T2, T3, T4, T5, R>
    Function<Tuple<Tuple<Tuple<Tuple<T1, T2>, T3>, T4>, T5>, R>
    to(Fun5<T1, T2, T3, T4, T5, R> f) {
        return p -> {
            final var t1 = p._1()._1()._1()._1();
            final var t2 = p._1()._1()._1()._2();
            final var t3 = p._1()._1()._2();
            final var t4 = p._1()._2();
            final var t5 = p._2();
            return f.apply(t1, t2, t3, t4, t5);
        };
    }

    public static <T1, T2, T3, T4, T5, T6, R>
    Function<Tuple<Tuple<Tuple<Tuple<Tuple<T1, T2>, T3>, T4>, T5>, T6>, R>
    to(Fun6<T1, T2, T3, T4, T5, T6, R> f) {
        return p -> {
            final var t1 = p._1()._1()._1()._1()._1();
            final var t2 = p._1()._1()._1()._1()._2();
            final var t3 = p._1()._1()._1()._2();
            final var t4 = p._1()._1()._2();
            final var t5 = p._1()._2();
            final var t6 = p._2();
            return f.apply(t1, t2, t3, t4, t5, t6);
        };
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R>
    Function<Tuple<Tuple<Tuple<Tuple<Tuple<Tuple<T1, T2>, T3>, T4>, T5>, T6>, T7>, R>
    to(Fun7<T1, T2, T3, T4, T5, T6, T7, R> f) {
        return p -> {
            final var t1 = p._1()._1()._1()._1()._1()._1();
            final var t2 = p._1()._1()._1()._1()._1()._2();
            final var t3 = p._1()._1()._1()._1()._2();
            final var t4 = p._1()._1()._1()._2();
            final var t5 = p._1()._1()._2();
            final var t6 = p._1()._2();
            final var t7 = p._2();
            return f.apply(t1, t2, t3, t4, t5, t6, t7);
        };
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R>
    Function<Tuple<Tuple<Tuple<Tuple<Tuple<Tuple<Tuple<T1, T2>, T3>, T4>, T5>, T6>, T7>, T8>, R>
    to(Fun8<T1, T2, T3, T4, T5, T6, T7, T8, R> f) {
        return p -> {
            final var t1 = p._1()._1()._1()._1()._1()._1()._1();
            final var t2 = p._1()._1()._1()._1()._1()._1()._2();
            final var t3 = p._1()._1()._1()._1()._1()._2();
            final var t4 = p._1()._1()._1()._1()._2();
            final var t5 = p._1()._1()._1()._2();
            final var t6 = p._1()._1()._2();
            final var t7 = p._1()._2();
            final var t8 = p._2();
            return f.apply(t1, t2, t3, t4, t5, t6, t7, t8);
        };
    }
}
