package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.ArrayList;
import java.util.List;

public class ManyParser<T> implements Parser<List<T>> {

  private final Parser<T> parser;
  
  public ManyParser(Parser<T> parser) {
    this.parser = parser;
  }

  @Override
  public <S> State<List<T>> parse(State<S> state, String value) {
//    return parser.parse(state, value)
//            .map2(s -> parse(s, value), (v, vs) -> { vs.add(v); return vs; })
//            .or(() -> State.result(state.pos, state.row, state.col, new ArrayList<>()));
    final var list = new ArrayList<T>();
    var nextState = parser.parse(state, value);
    if (!nextState.isPresent()) {
      return State.result(state.pos, state.row, state.col, list);
    }
    var prevState = nextState;
    while (nextState.isPresent()) {
      list.add(nextState.get());
      prevState = nextState;
      nextState = parser.parse(prevState, value);
    }
    return State.result(prevState.pos, prevState.row, prevState.col, list);
  }
}