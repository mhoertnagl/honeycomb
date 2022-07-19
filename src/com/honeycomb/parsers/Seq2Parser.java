package com.honeycomb.parsers;

import com.honeycomb.State;
import com.fundamentals.funs.Fun2;
import com.fundamentals.Tuples.*;

import static com.fundamentals.Tuples.tuple;

public record Seq2Parser<T1, T2>(
        Parser<T1> p1,
        Parser<T2> p2
) implements Parser<Tuple2<T1, T2>> {
    @Override
    public <S> State<Tuple2<T1, T2>> parse(State<S> state, String value) {
        final var s1 = p1.parse(state, value);
        if (s1.isError()) {
            return s1.map(v -> null);
        }

        final var s2 = p2.parse(s1, value);
        if (s2.isError()) {
            return s2.map(v -> null);
        }

        return s2.map(v -> tuple(
                s1.get(),
                s2.get()
        ));
    }

    public <U> Parser<U> map(Fun2<T1, T2, U> mapping) {
        return new Seq2MapParser<>(this, mapping);
    }

    public Parser<T1> _1() {
        return this.map((_1, _2) -> _1);
    }

    public Parser<T2> _2() {
        return this.map((_1, _2) -> _2);
    }

    private record Seq2MapParser<T1, T2, U>(
            Parser<Tuple2<T1, T2>> parser,
            Fun2<T1, T2, U> mapping
    ) implements Parser<U> {
        @Override
        public <S> State<U> parse(State<S> state, String value) {
            return parser.parse(state, value).map(t -> mapping.apply(
                    t._1(),
                    t._2()
            ));
        }
    }
}
