package com.fundamentals;

import java.util.*;

public class Prelude {

//    @SafeVarargs
//    public static <T> List<T> append(List<T> es, T... ts) {
//        es.addAll(Arrays.asList(ts));
//        return es;
//    }

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
}
