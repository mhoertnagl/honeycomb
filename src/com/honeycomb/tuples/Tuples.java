package com.honeycomb.tuples;

public abstract class Tuples {

    public static
    Tuple0 tuple() {
        return new Tuple0();
    }

    public static <T1>
    Tuple1<T1> tuple(T1 _1) {
        return new Tuple1<>(_1);
    }

    public static <T1, T2>
    Tuple2<T1, T2> tuple(T1 _1, T2 _2) {
        return new Tuple2<>(_1, _2);
    }

    public static <T1, T2, T3>
    Tuple3<T1, T2, T3> tuple(T1 _1, T2 _2, T3 _3) {
        return new Tuple3<>(_1, _2, _3);
    }

    public static <T1, T2, T3, T4>
    Tuple4<T1, T2, T3, T4> tuple(T1 _1, T2 _2, T3 _3, T4 _4) {
        return new Tuple4<>(_1, _2, _3, _4);
    }

    public static <T1, T2, T3, T4, T5>
    Tuple5<T1, T2, T3, T4, T5> tuple(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        return new Tuple5<>(_1, _2, _3, _4, _5);
    }

    public static <T1, T2, T3, T4, T5, T6>
    Tuple6<T1, T2, T3, T4, T5, T6> tuple(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        return new Tuple6<>(_1, _2, _3, _4, _5, _6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7>
    Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
        return new Tuple7<>(_1, _2, _3, _4, _5, _6, _7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8>
    Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) {
        return new Tuple8<>(_1, _2, _3, _4, _5, _6, _7, _8);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9>
    Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> tuple(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9) {
        return new Tuple9<>(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
    Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10
    ) {
        return new Tuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>
    Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> tuple(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11
    ) {
        return new Tuple11<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
    Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> tuple(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12
    ) {
        return new Tuple12<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
    Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> tuple(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13
    ) {
        return new Tuple13<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>
    Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> tuple(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14
    ) {
        return new Tuple14<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>
    Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> tuple(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15
    ) {
        return new Tuple15<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
    Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> tuple(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15, T16 _16
    ) {
        return new Tuple16<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16);
    }

    public record Tuple0() { }

    public record Tuple1<T1>(T1 _1) { }

    public record Tuple2<T1, T2>(T1 _1, T2 _2) { }

    public record Tuple3<T1, T2, T3>(T1 _1, T2 _2, T3 _3) { }

    public record Tuple4<T1, T2, T3, T4>(T1 _1, T2 _2, T3 _3, T4 _4) { }

    public record Tuple5<T1, T2, T3, T4, T5>(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) { }

    public record Tuple6<T1, T2, T3, T4, T5, T6>(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) { }

    public record Tuple7<T1, T2, T3, T4, T5, T6, T7>(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) { }

    public record Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) { }

    public record Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9
    ) {
    }

    public record Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10
    ) {
    }

    public record Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11
    ) {
    }

    public record Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12
    ) {
    }

    public record Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13
    ) {
    }

    public record Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14
    ) {
    }

    public record Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14,
            T15 _15
    ) {
    }

    public record Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(
            T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14,
            T15 _15, T16 _16
    ) {
    }
}