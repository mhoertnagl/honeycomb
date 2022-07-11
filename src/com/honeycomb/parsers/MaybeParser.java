package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.Optional;

public class MaybeParser<T> implements Parser<Optional<T>> {

  private final Parser<T> parser;
  
  public MaybeParser(Parser<T> parser) {
    this.parser = parser;
  }
  
  public <S> State<Optional<T>> parse(State<S> state, String value) {
    return parser.parse(state, value)
            .map(Optional::of)
            .or(() -> state.map(_v -> Optional.empty()));
  }
}