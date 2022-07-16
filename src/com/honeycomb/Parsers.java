package com.honeycomb;

import com.honeycomb.parsers.*;

import java.util.*;

public class Parsers {

    public static <U> Parser<U> WS() {
        return skip(regex("\\p{Z}*"));
    }

    public static <U> Parser<U> KW(String pattern) {
        return skip(literal(pattern));
    }

    public static Parser<String> literal(String pattern) {
        return new LiteralParser(pattern);
    }

    public static Parser<String> regex(String pattern) {
        return new RegexParser(pattern);
    }

    public static <T, U> Parser<U> skip(Parser<T> parser) {
        return parser.map(s -> null);
    }

    @SafeVarargs
    public static <T> Parser<T[]> seq(Parser<T> parser, Parser<T>... parsers) {
        return new SeqParser<>(parser, parsers);
    }

    @SafeVarargs
    public static <T> Parser<T> any(Parser<T>... parsers) {
        return new AnyParser<>(parsers);
    }

    public static <T> Parser<List<T>> many(Parser<T> parser) {
        return new ManyParser<>(parser);
    }

    public static <T> Parser<List<T>> many1(Parser<T> parser) {
        return seq(parser.map(Collections::singletonList), many(parser)).map(Parsers::flatten);
    }

    private static <T> List<T> flatten(Collection<T>[] lists) {
        final var res = new ArrayList<T>();
        for (final var list : lists) {
            if (list != null) {
                res.addAll(list);
            }
        }
        return res;
    }

//  private static <T> List<T> flatten(Iterable<? extends Iterable<T>> lists) {
//    final var res = new ArrayList<T>();
//    for (final var list : lists) {
//      if (list != null) {
//        for (final var elem : list) {
//          res.add(elem);
//        }
//      }
//    }
//    return res;
//  }

    private static <T> List<T> flatten(Iterable<T[]> lists) {
        final var res = new ArrayList<T>();
        for (final var list : lists) {
            if (list != null) {
                res.addAll(Arrays.stream(list).toList());
            }
        }
        return res;
    }

    public static <T> Parser<Optional<T>> maybe(Parser<T> parser) {
        return new MaybeParser<>(parser);
    }

    public static <T> Parser<Optional<List<T>>> list(String delim, Parser<T> parser) {
        return list(literal(delim), parser);
    }

    public static <S, T> Parser<Optional<List<T>>> list(Parser<S> delim, Parser<T> parser) {
        final var arg0 = parser.map(Collections::singletonList);
        final var argv = many(seq(skip(delim), parser)).map(Parsers::flatten);
        return maybe(seq(arg0, argv).map(Parsers::flatten));
    }
}

