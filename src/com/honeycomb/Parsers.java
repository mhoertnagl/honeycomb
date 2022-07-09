package com.honeycomb;

import com.honeycomb.parsers.*;

import java.util.List;

public class Parsers {

  public static Parser<String>  WS = regex("\\p{Z}*");
  
  public static Parser<String> literal(String pattern) {
    return new LiteralParser(pattern);
  }

  public static Parser<String>  regex(String pattern) {
    return new RegexParser(pattern);
  }
  
  @SafeVarargs
  public static <T> Parser<List<T>>  seq(Parser<T>... parsers) {
    return new SeqParser<>(parsers);
  }

  @SafeVarargs
  public static <T> Parser<T> any(Parser<T>... parsers) {
    return new AnyParser<>(parsers);
  }
  
  public static <T> Parser<List<T>>  many(Parser<T> parser) {
    return new ManyParser<>(parser);
  }

//  public static <T> Parser<List<T>>  many1(Parser<T>  parser) {
//    return seq(parser, many(parser));
//  }
  
//  public static Parser maybe(Parser parser) {
//    return new MaybeParser(parser);
//  }

//  public static Parser list(String delim, Parser parser) {
//    return list(literal(delim), parser);
//  }

//  public static Parser list(Parser delim, Parser parser) {
//    return maybe(seq(parser, many(seq(delim, parser))));
//  }
}

