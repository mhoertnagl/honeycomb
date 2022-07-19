package com.honeycomb.parsers;

import com.fundamentals.Tuples;
import com.fundamentals.funs.Fun8;
import com.honeycomb.State;

import static com.fundamentals.Tuples.tuple;

public record Seq8Parser<T1, T2, T3, T4, T5, T6, T7, T8>(
        Parser<T1> p1,
        Parser<T2> p2,
        Parser<T3> p3,
        Parser<T4> p4,
        Parser<T5> p5,
        Parser<T6> p6,
        Parser<T7> p7,
        Parser<T8> p8
) implements Parser<Tuples.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> {
    @Override
    public <S> State<Tuples.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> parse(State<S> state, String value) {
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

        return s8.map(v -> tuple(
                s1.get(),
                s2.get(),
                s3.get(),
                s4.get(),
                s5.get(),
                s6.get(),
                s7.get(),
                s8.get()
        ));

    }

    public <U> Parser<U> map(Fun8<T1, T2, T3, T4, T5, T6, T7, T8, U> mapping) {
        return new Seq8MapParser<>(this, mapping);
    }

    public Parser<T1> _1() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8) -> _1);
    }

    public Parser<T2> _2() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8) -> _2);
    }

    public Parser<T3> _3() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8) -> _3);
    }

    public Parser<T4> _4() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8) -> _4);
    }

    public Parser<T5> _5() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8) -> _5);
    }

    public Parser<T6> _6() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8) -> _6);
    }

    public Parser<T7> _7() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8) -> _7);
    }

    public Parser<T8> _8() {
        return this.map((_1, _2, _3, _4, _5, _6, _7, _8) -> _8);
    }

    private record Seq8MapParser<T1, T2, T3, T4, T5, T6, T7, T8, U>(
            Parser<Tuples.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> parser,
            Fun8<T1, T2, T3, T4, T5, T6, T7, T8, U> mapping
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
                    t._8()
            ));
        }
    }
}
