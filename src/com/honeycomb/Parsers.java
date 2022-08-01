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
    private static final String FLOAT_PATTERN =
            "[+-]?([0-9]+([.][0-9]*)?([eE][+-]?[0-9]+)?|[.][0-9]+([eE][+-]?[0-9]+)?)";

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
    public static final Parser<Integer> INT =
            regex(INT_PATTERN).map(Integer::parseInt);

    /** Parses a floating point number. */
    public static final Parser<Double> FLOAT =
            regex(FLOAT_PATTERN).map(Double::parseDouble);

    public static <T> Parser<T> succeed(T val) {
        return cur -> Optional.of(Parser.State.of(cur, val));
    }

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

    /**
     * Calls the {@link Supplier} and attempts to parse an optional value with
     * {@code parser} and if successful returns an {@link Optional} holding the
     * parsed value or {@link Optional#empty()} if {@code parser} fails. This
     * parser never fails and always returns a successful state.
     *
     * @param parser a {@link Supplier} that provides a {@link Parser}
     * @return a {@link Parser} that parses an optional value
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<Optional<T>> maybe(Supplier<Parser<T>> parser) {
        return cur -> maybe(parser.get()).parse(cur);
    }

    /**
     * Attempts to parse an optional value with {@code parser} and if
     * successful returns an {@link Optional} holding the parsed value or
     * {@link Optional#empty()} if {@code parser} fails. This parser never
     * fails and always returns a successful state.
     *
     * @param parser the {@link Parser} that may or may not succeed
     * @return a {@link Parser} that parses an optional value
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<Optional<T>> maybe(Parser<T> parser) {
        return parser.map(Optional::of).or(succeed(Optional.empty()));
    }

    /**
     * Calls the {@link Supplier} and attempts {@code parser} zero or more
     * times and returns a list containing zero or more parsed values. This
     * {@link Parser} never fails.
     *
     * @param parser the {@link Parser} to attempt as long as it matches
     * @return a {@link Parser} that is attemplted as often as possible
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> many(Supplier<Parser<T>> parser) {
        return cur -> many(parser.get()).parse(cur);
    }

    /**
     * Attempts {@code parser} zero or more times and returns a list containing
     * zero or more parsed values. This {@link Parser} never fails.
     *
     * @param parser the {@link Parser} to attempt as long as it matches
     * @return a {@link Parser} that is attempted as often as possible
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> many(Parser<T> parser) {
        return new ManyParser<>(parser);
    }

    /**
     * Calls the {@link Supplier} and attempts {@code parser} at least once
     * and returns a list containing at least one parsed value or fails if
     * {@code parser} did not match even once.
     *
     * @param parser the {@link Parser} that has to match at least once
     * @return a {@link Parser} that succeeds if {@code parser} matches at
     *         least once
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> many1(Supplier<Parser<T>> parser) {
        return cur -> many1(parser.get()).parse(cur);
    }

    /**
     * Attempts {@code parser} at least once and returns a list containing at
     * least one parsed value or fails if {@code parser} did not match even
     * once.
     *
     * @param parser the {@link Parser} that has to match at least once
     * @return a {@link Parser} that succeeds if {@code parser} matches at
     *         least once
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> many1(Parser<T> parser) {
        return parser.then(many(parser)).map(to(Prelude::prepend));
    }

    /**
     * Attempts all {@link Parser}s in order continuing until the first parser
     * matches or failing if all alternatives fail.
     *
     * @param parser first {@link Parser} to apply
     * @param parsers variable list of alternative {@link Parser}s
     * @return a {@link Parser} that tests all alternatives in order
     * @param <T> the common type of the parsed result value
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> Parser<T> any(
            Supplier<Parser<? extends T>> parser,
            Supplier<Parser<? extends T>>... parsers) {
        return cur -> Prelude.reduce(
                parsers,
                (Parser<T>) parser.get(),
                Parser::or
        ).parse(cur);
    }

    /**
     * Calls the {@link Supplier} and attempts all {@link Parser}s in order
     * continuing until the first parser matches or failing if all
     * alternatives fail.
     *
     * @param parser first {@link Parser} to apply
     * @param parsers variable list of alternative {@link Parser}s
     * @return a {@link Parser} that tests all alternatives in order
     * @param <T> the common type of the parsed result value
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> Parser<T> any(
            Parser<? extends T> parser,
            Parser<? extends T>... parsers) {
        return Prelude.reduce(parsers, (Parser<T>) parser, Parser::or);
    }

    public static <T> Parser<List<T>> list(
            String delimiter,
            Supplier<Parser<T>> parser) {
        return cur -> list(delimiter, parser.get()).parse(cur);
    }

    public static <T> Parser<List<T>> list(
            String delimiter,
            Parser<T> parser) {
        return list(literal(delimiter), parser);
    }

    public static <T> Parser<List<T>> list(
            Parser<?> delimiter,
            Supplier<Parser<T>> parser) {
        return cur -> list(delimiter, parser.get()).parse(cur);
    }

    public static <T> Parser<List<T>> list(
            Parser<?> delimiter,
            Parser<T> parser) {
        // Optional::get() makes the compiler complain,
        // therefore Optional::orElse(null).
        return maybe(list1(delimiter, parser))
                .map(o -> o.orElse(null))
                .or(succeed(List.of()));
    }

    public static <T> Parser<List<T>> list1(
            String delimiter,
            Supplier<Parser<T>> parser) {
        return cur -> list1(literal(delimiter), parser.get()).parse(cur);
    }

    public static <T> Parser<List<T>> list1(
            String delimiter,
            Parser<T> parser) {
        return list1(literal(delimiter), parser);
    }

    public static <T> Parser<List<T>> list1(
            Parser<?> delimiter,
            Supplier<Parser<T>> parser) {
        return cur -> list1(delimiter, parser.get()).parse(cur);
    }

    public static <T> Parser<List<T>> list1(
            Parser<?> delimiter,
            Parser<T> parser) {
        return parser.then(many(delimiter.skipLeft(parser)))
                .map(to(Prelude::prepend));
    }

    public static <T> Parser<T> between(
            String left,
            Supplier<Parser<T>> parser,
            String right) {
        return cur -> between(
                literal(left),
                parser.get(),
                literal(right)
        ).parse(cur);
    }

    public static <T> Parser<T> between(
            String left,
            Parser<T> parser,
            String right) {
        return between(literal(left), parser, literal(right));
    }

    public static <T> Parser<T> between(
            Parser<?> left,
            Supplier<Parser<T>> parser,
            Parser<?> right) {
        return cur -> between(left, parser.get(), right).parse(cur);
    }

    public static <T> Parser<T> between(
            Parser<?> left,
            Parser<T> parser,
            Parser<?> right) {
        return left.skipLeft(parser).skipRight(right);
    }

    public static final class LiteralParser implements Parser<String> {

        private final String pattern;

        public LiteralParser(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public Optional<State<? extends String>> parse(Cursor cur) {
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
        public Optional<State<? extends String>> parse(Cursor cur) {
            final var matcher = pattern.matcher(cur.in());
            // Define the start and end positions to be the current
            // parser offset and the end of the entire string.
            matcher.region(cur.pos(), cur.in().length());
            // See, if the pattern matches the input at the beginning
            // of the region as defined above.
            if (matcher.lookingAt()) {
                return Optional.of(State.of(
                        cur.positionAt(matcher.end()),
                        matcher.group())
                );
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
        public Optional<State<? extends List<T>>> parse(Cursor cur) {
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
