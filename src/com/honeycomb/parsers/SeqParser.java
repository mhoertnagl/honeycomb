package com.honeycomb.parsers;

import com.honeycomb.State;
import com.honeycomb.Tuple2;

import java.util.ArrayList;
import java.util.List;

import static com.honeycomb.Tuple.*;

public class SeqParser<T> implements Parser<List<T>> {

  private final Parser<T>[] parsers;
  
  @SafeVarargs
  public SeqParser(Parser<T>... parsers) {
    this.parsers = parsers;
  }
  
  public <S> State<List<T>> parse(State<S> state, String value) {
    var list = new ArrayList<T>();
    var curState = (State<?>)state;
    for (var parser : parsers) {
      curState = parser.parse(curState, value);
      if (curState instanceof State.ResultState)
      list.add(curState)
    }
    return state;
  }
}

public class Seq2Parser<T1, T2> implements Parser<Tuple2<T1, T2>> {

  private final Parser<T1> p1;
  private final Parser<T2> p2;

  public Seq2Parser(Parser<T1> p1, Parser<T2> p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  public <S> State<Tuple2<T1, T2>> parse(State<S> state, String value) {
    final var s1 = p1.parse(state, value);
    final var s2 = p2.parse(s1, value);
    // TODO: Return tuple if all were successful.
    return State.result(s2.pos, s2.row, s2.col, tuple(null, null));
  }
}