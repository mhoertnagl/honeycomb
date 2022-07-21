package com.honeycomb.shorter;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Parsers {

    public static final Parser<String> WS = regex("\\p{Z}*");

    public static final Parser<String> UID = regex("[\\p{L}][\\p{L}\\p{Nd}]*");

    public static final Parser<Integer> INT = regex("[+-]?[0-9]+").map(Integer::parseInt);

//    public static Parser<?> succeed() {
//        return in -> Optional.of(new Parser.Result<>(in, null));
//    }

    public static Parser<String> literal(String pattern) {
        return new LiteralParser(pattern);
    }

    public static Parser<String> regex(String pattern) {
        return new RegexParser(pattern);
    }

    @SafeVarargs
    public static <T> Parser<T> any(Supplier<Parser<T>> parser, Supplier<Parser<T>>... parsers) {
        return Arrays.stream(parsers).reduce(parser.get(), Parser::or, (_a, _b) -> null);
    }

    @SafeVarargs
    public static <T> Parser<T> any(Parser<T> parser, Parser<T>... parsers) {
        return Arrays.stream(parsers).reduce(parser, Parser::or);
    }

    public static <T1, T2, T3, R>
    Function<Pair<Pair<T1, T2>, T3>, R>
    to(Fun3<T1, T2, T3, R> f) {
        return p -> f.apply(p._1()._1(), p._1()._2(), p._2());
    }

    public static <T1, T2, T3>
    Function<Pair<Pair<T1, T2>, T3>, T2>
    take2of3() {
        return p -> p._1()._2();
    }

    public static class LiteralParser implements Parser<String> {

        private final String pattern;

        public LiteralParser(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public Optional<Result<String>> parse(Cursor in) {
            final var val = in.in();
            final var plen = pattern.length();
            final var vlen = val.length();
            final var pos = in.pos();
            if (pos < vlen && val.substring(pos).startsWith(pattern)) {
                return Optional.of(new Result<>(new Cursor(val, pos + plen), pattern));
            }
            return Optional.empty();
        }
    }

    public static class RegexParser implements Parser<String> {

        private static final int PATTERN_FLAGS =
                Pattern.UNICODE_CHARACTER_CLASS;

        private final Pattern pattern;

        public RegexParser(String regex) {
            this.pattern = Pattern.compile(regex, PATTERN_FLAGS);
        }

        @Override
        public Optional<Result<String>> parse(Cursor in) {
            final var matcher = pattern.matcher(in.in());
            // Define the start and end positions to be the current parser offset
            // and the end of the entire string.
            matcher.region(in.pos(), in.in().length());
            // See, if the pattern matches the input at the beginning of the
            // region as defined above.
            if (matcher.lookingAt()) {
                return Optional.of(new Result<>(new Cursor(in.in(), matcher.end()), matcher.group()));
            }
            return Optional.empty();
        }
    }

    @FunctionalInterface
    public interface Fun3<T1, T2, T3, R> {
        R apply(T1 t1, T2 t2, T3 t3);
    }
}
