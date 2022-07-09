package com.honeycomb;

public abstract class Tuple {

    public static <T1, T2> Tuple2<T1, T2> tuple(T1 t1, T2 t2) {
        return new Tuple2<>(t1, t2);
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> tuple(T1 t1, T2 t2, T3 t3) {
        return new Tuple3<>(t1, t2, t3);
    }
}

public final class Tuple2<T1, T2> {

    public final T1 _1;
    public final T2 _2;

    public Tuple2(T1 t1, T2 t2) {
        _1 = t1;
        _2 = t2;
    }
}

public final class Tuple3<T1, T2, T3> {

    public final T1 _1;
    public final T2 _2;
    public final T3 _3;

    public Tuple3(T1 t1, T2 t2, T3 t3) {
        _1 = t1;
        _2 = t2;
        _3 = t3;
    }
}
