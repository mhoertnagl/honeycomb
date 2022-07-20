package com.honeycomb.parsers;

import com.fundamentals.Prelude;
import com.honeycomb.State;

public class AnyParser<T> implements Parser<T> {

  private final Parser<? extends T>[] parsers;
  
  @SafeVarargs
  public AnyParser(Parser<? extends T>... parsers) {
    this.parsers = parsers;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <S> State<T> parse(State<S> state, String value) {
    return Prelude.reduce(parsers,
            state.error("Alternatives exhausted"),
            (s, p) -> s.or(() -> (State<T>) p.parse(state, value))
    );
  }
}

//public class Any2Parser<T1,T2> implements Parser<Tuple2<T1, T2>> {
//
//  private final Parser<T1> parser1;
//  private final Parser<T2> parser2;
//
//  public Any2Parser(Parser<T1> parser1, Parser<T2> parser2) {
//    this.parser1 = parser1;
//    this.parser2 = parser2;
//  }
//
//  public <S> State<Tuple2<T1, T2>> parse(State<S> state, String value)  {
//    final var state1 = parser1.parse(state, value);
//    if (state1.isValid()) {
//      return state1;
//    }
//
//    final var state2 = parser2.parse(state, value);
//    if (state2.isValid()) {
//      return state2;
//    }
//
//    return state
//            .error("Alternatives exhausted")
//            or(() -> parser1);
//  }
//}