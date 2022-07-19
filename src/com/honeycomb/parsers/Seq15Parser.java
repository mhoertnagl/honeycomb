package com.honeycomb.parsers;

import com.fundamentals.Tuples;
import com.fundamentals.funs.Fun15;
import com.honeycomb.State;

import static com.fundamentals.Tuples.tuple;

public record Seq15Parser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(
        Parser<T1> p1,
        Parser<T2> p2,
        Parser<T3> p3,
        Parser<T4> p4,
        Parser<T5> p5,
        Parser<T6> p6,
        Parser<T7> p7,
        Parser<T8> p8,
        Parser<T9> p9,
        Parser<T10> p10,
        Parser<T11> p11,
        Parser<T12> p12,
        Parser<T13> p13,
        Parser<T14> p14,
        Parser<T15> p15
) implements Parser<Tuples.Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> {
    @Override
    public <S> State<Tuples.Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> parse(State<S> state, String value) {
        final var s1 = p1.parse(state, value);
        if (s1.isError()) {
            return s1.map(v -> null);
        }

        final var s2 = p2.parse(s1, value);
        if (s2.isError()) {
            return s2.map(v -> null);
        }

        final var s3 = p3.parse(s2, value);
        if (s3.isError()) {
            return s3.map(v -> null);
        }

        final var s4 = p4.parse(s3, value);
        if (s4.isError()) {
            return s4.map(v -> null);
        }

        final var s5 = p5.parse(s4, value);
        if (s5.isError()) {
            return s5.map(v -> null);
        }

        final var s6 = p6.parse(s5, value);
        if (s6.isError()) {
            return s6.map(v -> null);
        }

        final var s7 = p7.parse(s6, value);
        if (s7.isError()) {
            return s7.map(v -> null);
        }

        final var s8 = p8.parse(s7, value);
        if (s8.isError()) {
            return s8.map(v -> null);
        }

        final var s9 = p9.parse(s8, value);
        if (s9.isError()) {
            return s9.map(v -> null);
        }

        final var s10 = p10.parse(s9, value);
        if (s10.isError()) {
            return s10.map(v -> null);
        }

        final var s11 = p11.parse(s10, value);
        if (s11.isError()) {
            return s11.map(v -> null);
        }

        final var s12 = p12.parse(s11, value);
        if (s12.isError()) {
            return s12.map(v -> null);
        }

        final var s13 = p13.parse(s12, value);
        if (s13.isError()) {
            return s13.map(v -> null);
        }

        final var s14 = p14.parse(s13, value);
        if (s14.isError()) {
            return s14.map(v -> null);
        }

        final var s15 = p15.parse(s14, value);
        if (s15.isError()) {
            return s15.map(v -> null);
        }

        return s15.map(v -> tuple(
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
        ));

    }

    public <U> Parser<U> map(Fun15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, U> mapping) {
        return new Seq15MapParser<>(this, mapping);
    }

    public Parser<T1> _1() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _1);
    }

    public Parser<T2> _2() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _2);
    }

    public Parser<T3> _3() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _3);
    }

    public Parser<T4> _4() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _4);
    }

    public Parser<T5> _5() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _5);
    }

    public Parser<T6> _6() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _6);
    }

    public Parser<T7> _7() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _7);
    }

    public Parser<T8> _8() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _8);
    }

    public Parser<T9> _9() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _9);
    }

    public Parser<T10> _10() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _10);
    }

    public Parser<T11> _11() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _11);
    }

    public Parser<T12> _12() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _12);
    }

    public Parser<T13> _13() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _13);
    }

    public Parser<T14> _14() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _14);
    }

    public Parser<T15> _15() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15) -> _15);
    }

    private record Seq15MapParser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, U>(
            Parser<Tuples.Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> parser,
            Fun15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, U> mapping
    ) implements Parser<U> {
        @Override
        public <S> State<U> parse(State<S> state, String value) {
            return parser.parse(state, value).map(t -> mapping.apply(
                    t._1(),
                    t._2(),
                    t._3(),
                    t._4(),
                    t._5(),
                    t._6(),
                    t._7(),
                    t._8(),
                    t._9(),
                    t._10(),
                    t._11(),
                    t._12(),
                    t._13(),
                    t._14(),
                    t._15()
            ));
        }
    }
}
