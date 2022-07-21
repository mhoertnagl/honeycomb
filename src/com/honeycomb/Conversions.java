package com.honeycomb;

import com.honeycomb.funs.*;

import java.util.function.Function;

import static com.honeycomb.Prelude.*;

public class Conversions {

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
}
