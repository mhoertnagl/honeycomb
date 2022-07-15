package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.ArrayList;
import java.util.List;

public class SeqParser<T> implements Parser<List<T>> {

  private final Parser<T>[] parsers;
  
  @SafeVarargs
  public SeqParser(Parser<T>... parsers) {
    this.parsers = parsers;
  }
  
  public <S> State<List<T>> parse(State<S> state, String value) {
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
    var list = new ArrayList<T>();
    var curState = parsers[0].parse(state, value);
    list.add(curState.get());
    for (var i = 1; i < parsers.length; i++) {
      final var parser = parsers[i];
      curState = parser.parse(curState, value);
      list.add(curState.get());
    }
    return State.result(curState.pos, curState.row, curState.col, list);
  }
}
