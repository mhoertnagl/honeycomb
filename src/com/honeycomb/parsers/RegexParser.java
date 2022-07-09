package com.honeycomb.parsers;

import java.util.function.Function;
import java.util.regex.Pattern;

import com.honeycomb.State;

public class RegexParser implements Parser<String> {

  private static final int PATTERN_FLAGS = 
    Pattern.UNICODE_CHARACTER_CLASS;

  private final Pattern pattern;
  
  public RegexParser(String regex) {
    this.pattern = Pattern.compile(regex, PATTERN_FLAGS);
  }

  @Override
  public <S> State<String> parse(State<S> state, String value) {
    final var matcher = pattern.matcher(value);
    // Define the start and end positions to be the current parser offset 
    // and the end of the entire string.
    matcher.region(state.pos, value.length());
    // See, if the pattern matches the input at the beginning of the 
    // region as defined above. 
    if (matcher.lookingAt()) {
      return State.result(matcher.end(), state.row, state.col, matcher.group());
    }
    return state.error("Expecting matching pattern [%s]", matcher.pattern());
  }

//  @Override
//  public <U> Parser<U> map(Function<String, U> mapping) {
//    return null;
//  }
}
