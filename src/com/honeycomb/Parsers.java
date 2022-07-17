package com.honeycomb;

import com.honeycomb.parsers.*;
import com.honeycomb.tuples.Tuples.*;

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

    public static <T1, T2> Seq2Parser<T1, T2> seq(Parser<T1> p1, Parser<T2> p2) {
        return new Seq2Parser<>(p1, p2);
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
        return seq(parser, many(parser)).map((v1, v2) -> { v2.add(0, v1); return v2; });
    }

    public static <T> Parser<Optional<T>> maybe(Parser<T> parser) {
        return new MaybeParser<>(parser);
    }

    public static <T> Parser<Optional<List<T>>> list(String delimiter, Parser<T> arg) {
        return list(literal(delimiter), arg);
    }

    public static <S, T> Parser<Optional<List<T>>> list(Parser<S> delimiter, Parser<T> arg) {
        return maybe(seq(arg, many(seq(delimiter, arg)._2())).map(Parsers::prepend));
    }

    private static <T> List<T> prepend(T v, List<T> list) {
        list.add(0, v);
        return list;
    }
}

