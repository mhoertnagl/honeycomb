package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.Arrays;

public class AnyParser<T> implements Parser<T> {

  private final Parser<? extends T>[] parsers;
  
  @SafeVarargs
  public AnyParser(Parser<? extends T>... parsers) {
    this.parsers = parsers;
  }

  @Override
  public <S> State<T> parse(State<S> state, String value) {
    return Arrays.stream(parsers).reduce(
            state.error("Alternatives exhausted"),
            // TODO: Undertyped.
            // (s, p) -> s.or(() -> (State<T>) p.parse(state, value)),
            (s, p) -> s.or(() -> p.parse(state, value)),
            (_a, _b) -> null
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