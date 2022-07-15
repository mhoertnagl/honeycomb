package com.honeycomb.parsers;

import com.honeycomb.State;
import com.honeycomb.tuples.Tuples;
import com.honeycomb.tuples.Tuples.*;

public class Seq3Parser<T1, T2, T3> implements Parser<Tuple3<T1, T2, T3>> {

  private final Parser<T1> p1;
  private final Parser<T2> p2;
  private final Parser<T3> p3;

  public Seq3Parser(Parser<T1> p1, Parser<T2> p2, Parser<T3> p3) {
    this.p1 = p1;
    this.p2 = p2;
    this.p3 = p3;
  }

  @Override
  public <S> State<Tuple3<T1, T2, T3>> parse(State<S> state, String value) {
    final var s1 = p1.parse(state, value);
    final var s2 = p2.parse(s1, value);
    final var s3 = p3.parse(s2, value);
    return State.result(
            s3.pos,
            s3.row,
            s3.col,
            State.whenAll(s1, s2, s3, Tuples::tuple)
    );
  }
}