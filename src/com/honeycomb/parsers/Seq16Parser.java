package com.honeycomb.parsers;

import com.honeycomb.State;
import com.fundamentals.funs.Fun16;
import com.fundamentals.Tuples;

import static com.fundamentals.Tuples.tuple;

public record Seq16Parser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(
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
        Parser<T15> p15,
        Parser<T16> p16
) implements Parser<Tuples.Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> {
    @Override
    public <S> State<Tuples.Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> parse(State<S> state, String value) {
        final var s1 = p1.parse(state, value);
        final var s2 = p2.parse(s1, value);
        final var s3 = p3.parse(s2, value);
        final var s4 = p4.parse(s3, value);
        final var s5 = p5.parse(s4, value);
        final var s6 = p6.parse(s5, value);
        final var s7 = p7.parse(s6, value);
        final var s8 = p8.parse(s7, value);
        final var s9 = p9.parse(s8, value);
        final var s10 = p10.parse(s9, value);
        final var s11 = p11.parse(s10, value);
        final var s12 = p12.parse(s11, value);
        final var s13 = p13.parse(s12, value);
        final var s14 = p14.parse(s13, value);
        final var s15 = p15.parse(s14, value);
        final var s16 = p16.parse(s15, value);
        return s1.flatMap(_1 ->
                s2.flatMap(_2 ->
                        s3.flatMap(_3 ->
                                s4.flatMap(_4 ->
                                        s5.flatMap(_5 ->
                                                s6.flatMap(_6 ->
                                                        s7.flatMap(_7 ->
                                                                s8.flatMap(_8 ->
                                                                        s9.flatMap(_9 ->
                                                                                s10.flatMap(_10 ->
                                                                                        s11.flatMap(_11 ->
                                                                                                s12.flatMap(_12 ->
                                                                                                        s13.flatMap(_13 ->
                                                                                                                s14.flatMap(_14 ->
                                                                                                                        s15.flatMap(_15 ->
                                                                                                                                s16.map(_16 -> tuple(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16)
                                                                                                                                ))))))))))))))));
    }

    public <U> Parser<U> map(Fun16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, U> mapping) {
        return new Seq16MapParser<>(this, mapping);
    }

    public Parser<T1> _1() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _1);
    }

    public Parser<T2> _2() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _2);
    }

    public Parser<T3> _3() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _3);
    }

    public Parser<T4> _4() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _4);
    }

    public Parser<T5> _5() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _5);
    }

    public Parser<T6> _6() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _6);
    }

    public Parser<T7> _7() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _7);
    }

    public Parser<T8> _8() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _8);
    }

    public Parser<T9> _9() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _9);
    }

    public Parser<T10> _10() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _10);
    }

    public Parser<T11> _11() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _11);
    }

    public Parser<T12> _12() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _12);
    }

    public Parser<T13> _13() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _13);
    }

    public Parser<T14> _14() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _14);
    }

    public Parser<T15> _15() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _15);
    }

    public Parser<T16> _16() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16) -> _16);
    }

    private record Seq16MapParser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, U>(
            Parser<Tuples.Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> parser,
            Fun16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, U> mapping
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
                    t._15(),
                    t._16()
            ));
        }
    }
}
