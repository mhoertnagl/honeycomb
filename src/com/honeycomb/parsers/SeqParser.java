package com.honeycomb.parsers;

import com.honeycomb.State;
import com.honeycomb.Tuple;
import com.honeycomb.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.honeycomb.Tuple.*;

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
    // return State.result(s2.pos, s2.row, s2.col, tuple(null, null));
    return State.result(
            s2.pos,
            s2.row,
            s2.col,
            State.whenAll(s1, s2, Tuple::tuple)
    );
  }
}