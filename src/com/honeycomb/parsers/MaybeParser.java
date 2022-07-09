package com.honeycomb.parsers;

import com.honeycomb.State;
import com.honeycomb.ParserException;

public class MaybeParser implements Parser {

  private final Parser parser;
  
  public MaybeParser(Parser parser) {
    this.parser = parser;
  }
  
  public State parse(State state, String value) {
    final var newState = parser.parse(state, value);
    return newState.isValid() ? newState : state;
  }
}