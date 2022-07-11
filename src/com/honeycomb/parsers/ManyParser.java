package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.List;

public class ManyParser<T> implements Parser<List<T>> {

  private final Parser<T> parser;
  
  public ManyParser(Parser<T> parser) {
    this.parser = parser;
  }

  @Override
  public <S> State<List<T>> parse(State<S> state, String value) {
    return null;
  }

//  public <S> State<T> parse(State<S> state, String value) {
//    var prevState = state;
//    var nextState = parser.parse(prevState, value);
//    while (nextState.isValid()) {
//      prevState = nextState;
//      nextState = parser.parse(prevState, value);
//    }
//    return prevState;
//  }
}