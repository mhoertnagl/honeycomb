package com.honeycomb.parsers;

import com.honeycomb.State;
import com.honeycomb.tuples.Tuples;
import com.honeycomb.tuples.Tuples.*;

public class Seq2Parser<T1, T2> implements Parser<Tuple2<T1, T2>> {

  private final Parser<T1> p1;
  private final Parser<T2> p2;

  public Seq2Parser(Parser<T1> p1, Parser<T2> p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  @Override
  public <S> State<Tuple2<T1, T2>> parse(State<S> state, String value) {
    final var s1 = p1.parse(state, value);
    final var s2 = p2.parse(s1, value);
    return State.result(
            s2.pos,
            s2.row,
            s2.col,
            State.whenAll(s1, s2, Tuples::tuple)
    );
  }
}