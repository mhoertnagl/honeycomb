package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.function.Function;

public interface Parser<T> {
  
  default State<T> parse(String value) {
    return parse(State.result(0, 1, 1, null), value);
  }

  <S> State<T> parse(State<S> state, String value);

  default <U> Parser<U> map(Function<T,U> mapping) {
    return new MapParser<>(this, mapping);
  }
}