package com.honeycomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static com.honeycomb.Conversions.to;

/**
 * A set of common predefined {@link Parser}s.
 *
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

    /** Parses zero or more Unicode white space characters. */
    public static final Parser<String> WS = regex(WS_PATTERN);

    /**
     * Parses a Unicode identifier. Requires a non-numeric character at the
     * beginning.
     */
    public static final Parser<String> UID = regex(UID_PATTERN);

    /** Parses an integer. */
    public static final Parser<Integer> INT =
            regex(INT_PATTERN).map(Integer::parseInt);

    /** Parses a floating point number. */
    public static final Parser<Double> FLOAT =
            regex(FLOAT_PATTERN).map(Double::parseDouble);

    public static <T> Parser<T> succeed(T val) {
        return cur -> State.of(cur, val);
    }

    /**
     * Parses the string literal {@code pattern}.
     *
     * @param literal the string to parse
     * @return a {@link Parser} that parses strings literal
     */
    public static Parser<String> literal(String literal) {
        return new LiteralParser(literal);
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
     * Calls the {@link Supplier} and attempts all {@link Parser}s in order
     * continuing until the first parser matches or failing if all
     * alternatives fail.
     *
     * @param parser first {@link Parser} to apply
     * @param parsers variable list of alternative {@link Parser}s
     * @return a {@link Parser} that tests all alternatives in order
     * @param <T> the type of the parsed result value
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
     * Attempts all {@link Parser}s in order continuing until the first parser
     * matches or failing if all alternatives fail.
     *
     * @param parser first {@link Parser} to apply
     * @param parsers variable list of alternative {@link Parser}s
     * @return a {@link Parser} that tests all alternatives in order
     * @param <T> the type of the parsed result value
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> Parser<T> any(
            Parser<? extends T> parser,
            Parser<? extends T>... parsers) {
        return Prelude.reduce(parsers, (Parser<T>) parser, Parser::or);
    }

    /**
     * Calls the {@link Supplier} and attempts {@link Parser} {@code parser}
     * in a list separated by a string {@code delimiter}. The final
     * {@link Parser} returns a list of parsed elements of type {@code T}.
     *
     * @param delimiter the delimiter string
     * @param parser {@link Parser} that parses the elements separated
     *        by delimiters
     * @return a {@link Parser} that parses delimited lists
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> list(
            String delimiter,
            Supplier<Parser<T>> parser) {
        return cur -> list(delimiter, parser.get()).parse(cur);
    }

    /**
     * Attempts {@link Parser} {@code parser} in a list separated by a string
     * {@code delimiter}. The final {@link Parser} returns
     * a list of parsed elements of type {@code T}.
     *
     * @param delimiter the delimiter string
     * @param parser {@link Parser} that parses the elements separated
     *        by delimiters
     * @return a {@link Parser} that parses delimited lists
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> list(
            String delimiter,
            Parser<T> parser) {
        return list(literal(delimiter), parser);
    }

    /**
     * Calls the {@link Supplier} and attempts {@link Parser} {@code parser}
     * in a list separated by a string {@code delimiter}. The final
     * {@link Parser} returns a list of parsed elements of type {@code T}.
     *
     * @param delimiter the delimiter string
     * @param parser {@link Parser} that parses the elements separated
     *        by delimiters
     * @return a {@link Parser} that parses delimited lists
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> list(
            Parser<?> delimiter,
            Supplier<Parser<T>> parser) {
        return cur -> list(delimiter, parser.get()).parse(cur);
    }

    /**
     * Attempts {@link Parser} {@code parser} in a list separated by delimiting
     * {@link Parser}s {@code delimiter}. The final {@link Parser} returns
     * a list of parsed elements of type {@code T}.
     *
     * @param delimiter {@link Parser} that parses the delimiter
     * @param parser {@link Parser} that parses the elements separated
     *        by delimiters
     * @return a {@link Parser} that parses delimited lists
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> list(
            Parser<?> delimiter,
            Parser<T> parser) {
        // Optional::get() makes the compiler complain,
        // therefore Optional::orElse(null).
        return maybe(list1(delimiter, parser))
                .map(o -> o.orElse(null))
                .or(succeed(List.of()));
    }

    /**
     * Calls the {@link Supplier} and attempts {@link Parser} {@code parser}
     * in a list separated by a string {@code delimiter}. The final
     * {@link Parser} returns a list of parsed elements of type {@code T}.
     * {@link Parser} {@code parser} has to match at least once or the final
     * {@link Parser} fails.
     *
     * @param delimiter the delimiter string
     * @param parser {@link Parser} that parses the elements separated
     *        by delimiters
     * @return a {@link Parser} that parses delimited lists
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> list1(
            String delimiter,
            Supplier<Parser<T>> parser) {
        return cur -> list1(literal(delimiter), parser.get()).parse(cur);
    }

    /**
     * Attempts {@link Parser} {@code parser} in a list separated by a string
     * {@code delimiter}. The final {@link Parser} returns a list of parsed
     * elements of type {@code T}.
     * {@link Parser} {@code parser} has to match at least once or the final
     * {@link Parser} fails.
     *
     * @param delimiter the delimiter string
     * @param parser {@link Parser} that parses the elements separated
     *        by delimiters
     * @return a {@link Parser} that parses delimited lists
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> list1(
            String delimiter,
            Parser<T> parser) {
        return list1(literal(delimiter), parser);
    }

    /**
     * Calls the {@link Supplier} and attempts {@link Parser} {@code parser}
     * in a list separated by delimiting {@link Parser}s {@code delimiter}.
     * The final {@link Parser} returns a list of parsed elements of type
     * {@code T}.
     * {@link Parser} {@code parser} has to match at least once or the final
     * {@link Parser} fails.
     *
     * @param delimiter {@link Parser} that parses the delimiter
     * @param parser {@link Parser} that parses the elements separated
     *        by delimiters
     * @return a {@link Parser} that parses delimited lists and matches at
     *         least once.
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> list1(
            Parser<?> delimiter,
            Supplier<Parser<T>> parser) {
        return cur -> list1(delimiter, parser.get()).parse(cur);
    }

    /**
     * Attempts {@link Parser} {@code parser} in a list separated by delimiting
     * {@link Parser}s {@code delimiter}. The final {@link Parser} returns
     * a list of parsed elements of type {@code T}.
     * {@link Parser} {@code parser} has to match at least once or the final
     * {@link Parser} fails.
     *
     * @param delimiter {@link Parser} that parses the delimiter
     * @param parser {@link Parser} that parses the elements separated
     *        by delimiters
     * @return a {@link Parser} that parses delimited lists and matches at
     *         least once.
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<List<T>> list1(
            Parser<?> delimiter,
            Parser<T> parser) {
        return parser.then(many(delimiter.skipLeft(parser)))
                .map(to(Prelude::prepend));
    }

    /**
     * Calls the {@link Supplier} and attempts {@link Parser} {@code parser}
     * between a {@code left} and a {@code right} delimiting string. Discards
     * the {@code left} and {@code right} result and returns the {@code parser}
     * value only.
     *
     * @param left the left delimiting string
     * @param parser the {@link Parser} in between
     * @param right the right delimiting string
     * @return a {@link Parser} surrounded by a left and a right delimiter
     * @param <T> the type of the parsed result value
     */
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

    /**
     * Attempts {@link Parser} {@code parser} between a {@code left} and a
     * {@code right} delimiting string. Discards the {@code left} and
     * {@code right} result and returns the {@code parser} value only.
     *
     * @param left the left delimiting string
     * @param parser the {@link Parser} in between
     * @param right the right delimiting string
     * @return a {@link Parser} surrounded by a left and a right delimiter
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<T> between(
            String left,
            Parser<T> parser,
            String right) {
        return between(literal(left), parser, literal(right));
    }

    /**
     * Calls the {@link Supplier} and attempts {@link Parser} {@code parser}
     * between a {@code left} and a {@code right} {@link Parser}. Discards
     * the {@code left} and {@code right} result and returns the {@code parser}
     * value only.
     *
     * @param left the left delimiting {@link Parser}
     * @param parser the {@link Parser} in between
     * @param right the right delimiting {@link Parser}
     * @return a {@link Parser} surrounded by a left and a right {@link Parser}
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<T> between(
            Parser<?> left,
            Supplier<Parser<T>> parser,
            Parser<?> right) {
        return cur -> between(left, parser.get(), right).parse(cur);
    }

    /**
     * Attempts {@link Parser} {@code parser} between a {@code left} and a
     * {@code right} {@link Parser}. Discards the {@code left} and
     * {@code right} result and returns the {@code parser} value only.
     *
     * @param left the left delimiting {@link Parser}
     * @param parser the {@link Parser} in between
     * @param right the right delimiting {@link Parser}
     * @return a {@link Parser} surrounded by a left and a right {@link Parser}
     * @param <T> the type of the parsed result value
     */
    public static <T> Parser<T> between(
            Parser<?> left,
            Parser<T> parser,
            Parser<?> right) {
        return left.skipLeft(parser).skipRight(right);
    }
}
