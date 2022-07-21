package com.honeycomb;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Parsers {

    public static final Parser<String> WS = regex("\\p{Z}*");

    public static final Parser<String> UID = regex("[\\p{L}][\\p{L}\\p{Nd}]*");

    public static final Parser<Integer> INT = regex("[+-]?[0-9]+").map(Integer::parseInt);

    // TODO: use cases for succeed?
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
        return Prelude.reduce(parsers, parser.get(), Parser::or);
    }

    @SafeVarargs
    public static <T> Parser<T> any(Parser<T> parser, Parser<T>... parsers) {
        return Prelude.reduce(parsers, parser, Parser::or);
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
}
