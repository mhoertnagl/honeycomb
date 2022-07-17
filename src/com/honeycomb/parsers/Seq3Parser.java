package com.honeycomb.parsers;

import com.honeycomb.State;
import com.fundamentals.funs.Fun3;
import com.fundamentals.Tuples;

import static com.fundamentals.Tuples.tuple;

public record Seq3Parser<T1, T2, T3>(
        Parser<T1> p1,
        Parser<T2> p2,
        Parser<T3> p3
) implements Parser<Tuples.Tuple3<T1, T2, T3>> {
    @Override
    public <S> State<Tuples.Tuple3<T1, T2, T3>> parse(State<S> state, String value) {
        final var s1 = p1.parse(state, value);
        final var s2 = p2.parse(s1, value);
        final var s3 = p3.parse(s2, value);
        return s1.flatMap(_1 ->
                s2.flatMap(_2 ->
                        s3.map(_3 -> tuple(_1, _2, _3)
                        )));
    }

    public <U> Parser<U> map(Fun3<T1, T2, T3, U> mapping) {
        return new Seq3MapParser<>(this, mapping);
    }

    public Parser<T1> _1() {
        return this.map((_1, _2, _3) -> _1);
    }

    public Parser<T2> _2() {
        return this.map((_1, _2, _3) -> _2);
    }

    public Parser<T3> _3() {
        return this.map((_1, _2, _3) -> _3);
    }

    private record Seq3MapParser<T1, T2, T3, U>(
            Parser<Tuples.Tuple3<T1, T2, T3>> parser,
            Fun3<T1, T2, T3, U> mapping
    ) implements Parser<U> {
        @Override
        public <S> State<U> parse(State<S> state, String value) {
            return parser.parse(state, value).map(t -> mapping.apply(
                    t._1(),
                    t._2(),
                    t._3()
            ));
        }
    }
}
