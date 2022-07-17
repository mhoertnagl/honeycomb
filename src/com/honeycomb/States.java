package com.honeycomb;

import com.honeycomb.funs.*;

public abstract class States {

    public static <T1, T2, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            Fun2<T1, T2, R> fun) {
        if (s1.isPresent() && s2.isPresent()) {
            return fun.apply(s1.get(), s2.get());
        }
        return null;
    }

    public static <T1, T2, T3, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            Fun3<T1, T2, T3, R> fun) {
        if (s1.isPresent() && s2.isPresent() && s3.isPresent()) {
            return fun.apply(s1.get(), s2.get(), s3.get());
        }
        return null;
    }

    public static <T1, T2, T3, T4, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            Fun4<T1, T2, T3, T4, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            Fun5<T1, T2, T3, T4, T5, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            Fun6<T1, T2, T3, T4, T5, T6, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            State<T7> s7,
            Fun7<T1, T2, T3, T4, T5, T6, T7, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
                && s7.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get(),
                    s7.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            State<T7> s7,
            State<T8> s8,
            Fun8<T1, T2, T3, T4, T5, T6, T7, T8, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
                && s7.isPresent()
                && s8.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get(),
                    s7.get(),
                    s8.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            State<T7> s7,
            State<T8> s8,
            State<T9> s9,
            Fun9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
                && s7.isPresent()
                && s8.isPresent()
                && s9.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get(),
                    s7.get(),
                    s8.get(),
                    s9.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            State<T7> s7,
            State<T8> s8,
            State<T9> s9,
            State<T10> s10,
            Fun10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
                && s7.isPresent()
                && s8.isPresent()
                && s9.isPresent()
                && s10.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get(),
                    s7.get(),
                    s8.get(),
                    s9.get(),
                    s10.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            State<T7> s7,
            State<T8> s8,
            State<T9> s9,
            State<T10> s10,
            State<T11> s11,
            Fun11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
                && s7.isPresent()
                && s8.isPresent()
                && s9.isPresent()
                && s10.isPresent()
                && s11.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get(),
                    s7.get(),
                    s8.get(),
                    s9.get(),
                    s10.get(),
                    s11.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            State<T7> s7,
            State<T8> s8,
            State<T9> s9,
            State<T10> s10,
            State<T11> s11,
            State<T12> s12,
            Fun12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
                && s7.isPresent()
                && s8.isPresent()
                && s9.isPresent()
                && s10.isPresent()
                && s11.isPresent()
                && s12.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get(),
                    s7.get(),
                    s8.get(),
                    s9.get(),
                    s10.get(),
                    s11.get(),
                    s12.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            State<T7> s7,
            State<T8> s8,
            State<T9> s9,
            State<T10> s10,
            State<T11> s11,
            State<T12> s12,
            State<T13> s13,
            Fun13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
                && s7.isPresent()
                && s8.isPresent()
                && s9.isPresent()
                && s10.isPresent()
                && s11.isPresent()
                && s12.isPresent()
                && s13.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get(),
                    s7.get(),
                    s8.get(),
                    s9.get(),
                    s10.get(),
                    s11.get(),
                    s12.get(),
                    s13.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            State<T7> s7,
            State<T8> s8,
            State<T9> s9,
            State<T10> s10,
            State<T11> s11,
            State<T12> s12,
            State<T13> s13,
            State<T14> s14,
            Fun14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
                && s7.isPresent()
                && s8.isPresent()
                && s9.isPresent()
                && s10.isPresent()
                && s11.isPresent()
                && s12.isPresent()
                && s13.isPresent()
                && s14.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get(),
                    s7.get(),
                    s8.get(),
                    s9.get(),
                    s10.get(),
                    s11.get(),
                    s12.get(),
                    s13.get(),
                    s14.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            State<T7> s7,
            State<T8> s8,
            State<T9> s9,
            State<T10> s10,
            State<T11> s11,
            State<T12> s12,
            State<T13> s13,
            State<T14> s14,
            State<T15> s15,
            Fun15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
                && s7.isPresent()
                && s8.isPresent()
                && s9.isPresent()
                && s10.isPresent()
                && s11.isPresent()
                && s12.isPresent()
                && s13.isPresent()
                && s14.isPresent()
                && s15.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get(),
                    s7.get(),
                    s8.get(),
                    s9.get(),
                    s10.get(),
                    s11.get(),
                    s12.get(),
                    s13.get(),
                    s14.get(),
                    s15.get()
            );
        }
        return null;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> R whenAll(
            State<T1> s1,
            State<T2> s2,
            State<T3> s3,
            State<T4> s4,
            State<T5> s5,
            State<T6> s6,
            State<T7> s7,
            State<T8> s8,
            State<T9> s9,
            State<T10> s10,
            State<T11> s11,
            State<T12> s12,
            State<T13> s13,
            State<T14> s14,
            State<T15> s15,
            State<T16> s16,
            Fun16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> fun) {
        if (s1.isPresent()
                && s2.isPresent()
                && s3.isPresent()
                && s4.isPresent()
                && s5.isPresent()
                && s6.isPresent()
                && s7.isPresent()
                && s8.isPresent()
                && s9.isPresent()
                && s10.isPresent()
                && s11.isPresent()
                && s12.isPresent()
                && s13.isPresent()
                && s14.isPresent()
                && s15.isPresent()
                && s16.isPresent()
        ) {
            return fun.apply(
                    s1.get(),
                    s2.get(),
                    s3.get(),
                    s4.get(),
                    s5.get(),
                    s6.get(),
                    s7.get(),
                    s8.get(),
                    s9.get(),
                    s10.get(),
                    s11.get(),
                    s12.get(),
                    s13.get(),
                    s14.get(),
                    s15.get(),
                    s16.get()
            );
        }
        return null;
    }
}
