package com.honeycomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Parsers {

    public static final Parser<String> WS = regex("\\p{Z}*");

    public static final Parser<String> UID = regex("[\\p{L}][\\p{L}\\p{Nd}]*");

    public static final Parser<Integer> INT = regex("[+-]?[0-9]+").map(Integer::parseInt);

    public static <T> Parser<T> succeed(T val) {
        return cur -> Optional.of(Parser.Result.of(cur, val));
    }

    public static Parser<String> literal(String pattern) {
        return new LiteralParser(pattern);
    }

    public static Parser<String> regex(String pattern) {
        return new RegexParser(pattern);
    }

    public static <T> Parser<Optional<T>> maybe(Supplier<Parser<T>> parser) {
        return cur -> maybe(parser.get()).parse(cur);
    }

    public static <T> Parser<Optional<T>> maybe(Parser<T> parser) {
        return cur -> parser.parse(cur).map(r -> r.map(Optional::of));
    }

    @SafeVarargs
    public static <T> Parser<T> any(Supplier<Parser<T>> parser, Supplier<Parser<T>>... parsers) {
        return Prelude.reduce(parsers, parser.get(), Parser::or);
    }

    @SafeVarargs
    public static <T> Parser<T> any(Parser<T> parser, Parser<T>... parsers) {
        return Prelude.reduce(parsers, parser, Parser::or);
    }

    public static <T> Parser<List<T>> many(Supplier<Parser<T>> parser) {
        return cur -> many(parser.get()).parse(cur);
    }

    public static <T> Parser<List<T>> many(Parser<T> parser) {

    }

    public static <T> Parser<List<T>> many1(Supplier<Parser<T>> parser) {
        return cur -> many1(parser.get()).parse(cur);
    }

    public static <T> Parser<List<T>> many1(Parser<T> parser) {

    }

    public static class LiteralParser implements Parser<String> {

        private final String pattern;

        public LiteralParser(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public Optional<Result<String>> parse(Cursor cur) {
            final var val = cur.in();
            final var plen = pattern.length();
            final var vlen = val.length();
            final var pos = cur.pos();
            if (pos < vlen && val.substring(pos).startsWith(pattern)) {
                return Optional.of(Result.of(cur.advanceBy(plen), pattern));
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
        public Optional<Result<String>> parse(Cursor cur) {
            final var matcher = pattern.matcher(cur.in());
            // Define the start and end positions to be the current parser offset
            // and the end of the entire string.
            matcher.region(cur.pos(), cur.in().length());
            // See, if the pattern matches the input at the beginning of the
            // region as defined above.
            if (matcher.lookingAt()) {
                return Optional.of(Result.of(cur.positionAt(matcher.end()), matcher.group()));
            }
            return Optional.empty();
        }
    }

    public static class ManyParser<T> implements Parser<List<T>> {

        private final Parser<T> parser;

        public ManyParser(Parser<T> parser) {
            this.parser = parser;
        }

        @Override
        public Optional<Result<List<T>>> parse(Cursor cur) {
            final var list = new ArrayList<T>();
            Optional<Result<T>> res = null;
            do {
                res = parser.parse(cur);
            } while ()
            final var val = cur.in();
            final var plen = pattern.length();
            final var vlen = val.length();
            final var pos = cur.pos();
            if (pos < vlen && val.substring(pos).startsWith(pattern)) {
                return Optional.of(Result.of(cur.advanceBy(plen), pattern));
            }
            return Optional.empty();
        }
    }
}
