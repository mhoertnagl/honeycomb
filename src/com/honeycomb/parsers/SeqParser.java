package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.ArrayList;
import java.util.Collection;

public class SeqParser<T> implements Parser<T[]> {

  private final Parser<T> parser;
  private final Parser<T>[] parsers;
  
  @SafeVarargs
  public SeqParser(Parser<T> parser, Parser<T>... parsers) {
    this.parser = parser;
    this.parsers = parsers;
  }

  @SuppressWarnings("unchecked")
  public SeqParser(Parser<T> parser, Collection<Parser<T>> parsers) {
    this.parser = parser;
    this.parsers = (Parser<T>[]) parsers.toArray();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <S> State<T[]> parse(State<S> state, String value) {
    var list = new ArrayList<T>();
    var curState = parser.parse(state, value);
    list.add(curState.get());
    for (var parser: parsers) {
      curState = parser.parse(curState, value);
      list.add(curState.get());
    }
    return State.result(curState.pos, curState.row, curState.col, (T[]) list.toArray());
  }
}

//    return Arrays.stream(parsers).reduce(
//            State.result(state.pos, state.row, state.col, new ArrayList<T>()),
//            (s, p) -> {
//              final var s1 = p.parse(s, value);
//              final var l = s.get();
//              l.add(s1.get());
//              return State.result(s1.pos, s1.row, s1.col, l);
//            },
//            (_a, _b) -> null
//    );
