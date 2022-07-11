package com.honeycomb.parsers;

import com.honeycomb.State;

public class LiteralParser implements Parser<String> {

  private final String pattern;
  
  public LiteralParser(String pattern) {
    this.pattern = pattern;
  }

  @Override
  public <S> State<String> parse(State<S> state, String value) {
    final var plen = pattern.length();
    final var vlen = value.length();

    if (plen > vlen) {
      return state.error("Expected [%s]", pattern);
    }

    var pos = state.pos;
    final var len = pos + plen;
    while (pos < len) {
      final var pcp = pattern.codePointAt(pos);
      final var vcp = value.codePointAt(pos);

      // TODO: Do we allow newlines in strings?
      // TODO: Increment line count.
      
      if (pcp != vcp) {
        return state.error("Expected [%s]", pattern);
      }

      pos += Character.charCount(vcp);
    }

    return State.result(pos, state.row, state.col + plen, pattern);
  }
}
