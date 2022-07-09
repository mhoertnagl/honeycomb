package com.honeycomb.parsers;

import com.honeycomb.State;
import com.honeycomb.ParserException;

public class ManyParser<T> implements Parser<T> {

  private final Parser parser;
  
  public ManyParser(Parser parser) {
    this.parser = parser;
  }
  
  public <S> State<T> parse(State<S> state, String value) {
    var prevState = state;
    var nextState = parser.parse(prevState, value);
    while (nextState.isValid()) {
      prevState = nextState;
      nextState = parser.parse(prevState, value);
    }
    return prevState;
  }
}