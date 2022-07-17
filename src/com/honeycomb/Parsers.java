package com.honeycomb;

import com.fundamentals.Prelude;
import com.honeycomb.parsers.*;

import java.util.*;

public class Parsers {

    public static final Parser<String> WS =  regex("\\p{Z}*");

//    public static Parser<String> WS() {
//        return regex("\\p{Z}*");
//    }

//    public static <U> Parser<U> WS() {
//        return skip(regex("\\p{Z}*"));
//    }

//    public static <U> Parser<U> KW(String pattern) {
//        return skip(literal(pattern));
//    }

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

    public static <T1, T2, T3> Seq3Parser<T1, T2, T3>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3
    ) {
        return new Seq3Parser<>(p1, p2, p3);
    }

    public static <T1, T2, T3, T4> Seq4Parser<T1, T2, T3, T4>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4
    ) {
        return new Seq4Parser<>(p1, p2, p3, p4);
    }

    public static <T1, T2, T3, T4, T5> Seq5Parser<T1, T2, T3, T4, T5>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5
    ) {
        return new Seq5Parser<>(p1, p2, p3, p4, p5);
    }

    public static <T1, T2, T3, T4, T5, T6> Seq6Parser<T1, T2, T3, T4, T5, T6>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6
    ) {
        return new Seq6Parser<>(p1, p2, p3, p4, p5, p6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Seq7Parser<T1, T2, T3, T4, T5, T6, T7>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6,
            Parser<T7> p7
    ) {
        return new Seq7Parser<>(p1, p2, p3, p4, p5, p6, p7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Seq8Parser<T1, T2, T3, T4, T5, T6, T7, T8>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6,
            Parser<T7> p7,
            Parser<T8> p8
    ) {
        return new Seq8Parser<>(p1, p2, p3, p4, p5, p6, p7, p8);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Seq9Parser<T1, T2, T3, T4, T5, T6, T7, T8, T9>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6,
            Parser<T7> p7,
            Parser<T8> p8,
            Parser<T9> p9
    ) {
        return new Seq9Parser<>(p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Seq10Parser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6,
            Parser<T7> p7,
            Parser<T8> p8,
            Parser<T9> p9,
            Parser<T10> p10
    ) {
        return new Seq10Parser<>(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>
    Seq11Parser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6,
            Parser<T7> p7,
            Parser<T8> p8,
            Parser<T9> p9,
            Parser<T10> p10,
            Parser<T11> p11
    ) {
        return new Seq11Parser<>(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
    Seq12Parser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6,
            Parser<T7> p7,
            Parser<T8> p8,
            Parser<T9> p9,
            Parser<T10> p10,
            Parser<T11> p11,
            Parser<T12> p12
    ) {
        return new Seq12Parser<>(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
    Seq13Parser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6,
            Parser<T7> p7,
            Parser<T8> p8,
            Parser<T9> p9,
            Parser<T10> p10,
            Parser<T11> p11,
            Parser<T12> p12,
            Parser<T13> p13
    ) {
        return new Seq13Parser<>(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>
    Seq14Parser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6,
            Parser<T7> p7,
            Parser<T8> p8,
            Parser<T9> p9,
            Parser<T10> p10,
            Parser<T11> p11,
            Parser<T12> p12,
            Parser<T13> p13,
            Parser<T14> p14
    ) {
        return new Seq14Parser<>(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>
    Seq15Parser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6,
            Parser<T7> p7,
            Parser<T8> p8,
            Parser<T9> p9,
            Parser<T10> p10,
            Parser<T11> p11,
            Parser<T12> p12,
            Parser<T13> p13,
            Parser<T14> p14,
            Parser<T15> p15
    ) {
        return new Seq15Parser<>(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
    Seq16Parser<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
    seq(
            Parser<T1> p1,
            Parser<T2> p2,
            Parser<T3> p3,
            Parser<T4> p4,
            Parser<T5> p5,
            Parser<T6> p6,
            Parser<T7> p7,
            Parser<T8> p8,
            Parser<T9> p9,
            Parser<T10> p10,
            Parser<T11> p11,
            Parser<T12> p12,
            Parser<T13> p13,
            Parser<T14> p14,
            Parser<T15> p15,
            Parser<T16> p16
    ) {
        return new Seq16Parser<>(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16);
    }

    @SafeVarargs
    public static <T> Parser<T[]> seq(Parser<T> parser, Parser<T>... parsers) {
        return new SeqParser<>(parser, parsers);
    }

    // TODO: wseq(..) for whitespaced sequences.

    @SafeVarargs
    public static <T> Parser<T> any(Parser<T>... parsers) {
        return new AnyParser<>(parsers);
    }

    public static <T> Parser<List<T>> many(Parser<T> parser) {
        return new ManyParser<>(parser);
    }

    public static <T> Parser<List<T>> many1(Parser<T> parser) {
        return seq(parser, many(parser)).map(Prelude::prepend);
    }

    public static <T> Parser<Optional<T>> maybe(Parser<T> parser) {
        return new MaybeParser<>(parser);
    }

    public static <T> Parser<Optional<List<T>>> list(String delimiter, Parser<T> arg) {
        return list(literal(delimiter), arg);
    }

    public static <S, T> Parser<Optional<List<T>>> list(Parser<S> delimiter, Parser<T> arg) {
        return maybe(seq(arg, many(seq(delimiter, arg)._2())).map(Prelude::prepend));
    }
}

