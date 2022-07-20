package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Parser<T> {
  
  default State<? extends T> parse(String value) {
    return parse(State.result(0, 1, 1, null), value);
  }

  <S> State<? extends T> parse(State<S> state, String value);

  default <U> Parser<U> map(Function<? super T,? extends U> mapping) {
    return new MapParser<>(this, mapping);
  }
}