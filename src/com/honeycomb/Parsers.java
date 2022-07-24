package com.honeycomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static com.honeycomb.Conversions.to;

/**
 * @since 1.0
 */
public final class Parsers {

    /** Unicode white space pattern. */
    private static final String WS_PATTERN = "\\p{Z}*";

    /** Pattern for Unicode identifiers. */
    private static final String UID_PATTERN = "[\\p{L}][\\p{L}\\p{Nd}]*";

    /** Pattern for integers. */
    private static final String INT_PATTERN = "[+-]?[0-9]+";

    /** Pattern for floating point numbers. */
    private static final String FLOAT_PATTERN = "[+-]?([0-9]+([.][0-9]*)?([eE][+-]?[0-9]+)?|[.][0-9]+([eE][+-]?[0-9]+)?)";

    /**
     * Parses zero or more Unicode white space characters.
     */
    public static final Parser<String> WS = regex(WS_PATTERN);

    /**
     * Parses a Unicode identifier. Requires a non-numeric character at the
     * beginning.
     */
    public static final Parser<String> UID = regex(UID_PATTERN);

    /**
     * Parses an integer.
     */
    public static final Parser<Integer> INT = regex(INT_PATTERN).map(Integer::parseInt);

    /**
     * Parses a floating point number.
     */
    public static final Parser<Double> FLOAT = regex(FLOAT_PATTERN).map(Double::parseDouble);

//    public static <T> Parser<T> succeed(T val) {
//        return cur -> Optional.of(Parser.State.of(cur, val));
//    }

    /**
     * Parses the string literal {@code pattern}.
     *
     * @param pattern the string to parse
     * @return a {@link Parser} that parses strings literal
     */
    public static Parser<String> literal(String pattern) {
        return new LiteralParser(pattern);
    }

    /**
     * Parses input that matches the regex {@code pattern}.
     *
     * @param pattern the regex pattern
     * @return a {@link Parser} that parses the regex pattern
     */
    public static Parser<String> regex(String pattern) {
        return new RegexParser(pattern);
    }

    public static <T> Parser<Optional<T>> maybe(Supplier<Parser<T>> parser) {
        return maybe(parser.get());
        // return cur -> maybe(parser.get()).parse(cur);
    }

    // TODO: Does maybe work?
    public static <T> Parser<Optional<T>> maybe(Parser<T> parser) {
        return parser.map(Optional::of);
        // return cur -> parser.parse(cur).map(r -> r.map(Optional::of));
    }

    public static <T> Parser<List<T>> many(Supplier<Parser<T>> parser) {
        return many(parser.get());
        // return cur -> many(parser.get()).parse(cur);
    }

    public static <T> Parser<List<T>> many(Parser<T> parser) {
        return new ManyParser<>(parser);
    }

    public static <T> Parser<List<T>> many1(Supplier<Parser<T>> parser) {
        return many1(parser.get());
        // return cur -> many1(parser.get()).parse(cur);
    }

    public static <T> Parser<List<T>> many1(Parser<T> parser) {
        return parser.then(many(parser)).map(to(Prelude::prepend));
        // return cur -> parser.then(many(parser)).parse(cur).map(s -> s.map(to(Prelude::prepend)));
    }

    @SafeVarargs
    public static <T> Parser<T> any(Supplier<Parser<T>> parser, Supplier<Parser<T>>... parsers) {
        return Prelude.reduce(parsers, parser.get(), Parser::or);
    }

    @SafeVarargs
    public static <T> Parser<T> any(Parser<T> parser, Parser<T>... parsers) {
        return Prelude.reduce(parsers, parser, Parser::or);
    }

    public static final class LiteralParser implements Parser<String> {

        private final String pattern;

        public LiteralParser(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public Optional<State<String>> parse(Cursor cur) {
            final var pos = cur.pos();
            final var val = cur.in();
            final var pln = pattern.length();
            final var vln = val.length();
            if (pos < vln && val.substring(pos).startsWith(pattern)) {
                return Optional.of(State.of(cur.advanceBy(pln), pattern));
            }
            return Optional.empty();
        }
    }

    public static final class RegexParser implements Parser<String> {

        private static final int PATTERN_FLAGS = Pattern.UNICODE_CHARACTER_CLASS;

        private final Pattern pattern;

        public RegexParser(String regex) {
            this.pattern = Pattern.compile(regex, PATTERN_FLAGS);
        }

        @Override
        public Optional<State<String>> parse(Cursor cur) {
            final var matcher = pattern.matcher(cur.in());
            // Define the start and end positions to be the current parser offset
            // and the end of the entire string.
            matcher.region(cur.pos(), cur.in().length());
            // See, if the pattern matches the input at the beginning of the
            // region as defined above.
            if (matcher.lookingAt()) {
                return Optional.of(State.of(cur.positionAt(matcher.end()), matcher.group()));
            }
            return Optional.empty();
        }
    }

    public static final class ManyParser<T> implements Parser<List<T>> {

        private final Parser<T> parser;

        public ManyParser(Parser<T> parser) {
            this.parser = parser;
        }

        @Override
        public Optional<State<List<T>>> parse(Cursor cur) {
            final var list = new ArrayList<T>();
            var state = parser.parse(cur);
            while (state.isPresent()) {
                list.add(state.get().val());
                cur = state.get().cur();
                state = parser.parse(cur);
            }
            return Optional.of(State.of(cur, list));
        }
    }
}
