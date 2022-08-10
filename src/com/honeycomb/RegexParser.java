package com.honeycomb;

import java.util.regex.Pattern;

/**
 * A {@link Parser} that accepts a regular expression string and succeeds
 * if the pattern matches the input or failing otherwise. The regular
 * expression allows Unicode characters.
 */
public final class RegexParser implements Parser<String> {

    private static final int PATTERN_FLAGS =
            Pattern.UNICODE_CHARACTER_CLASS;

    private final Pattern pattern;

    /**
     * Creates a new regex {@link Parser} parsing the regular expression
     * {@code regex}. The regular expression allows Unicode characters.
     *
     * @param regex the regular expression
     */
    public RegexParser(String regex) {
        this.pattern = Pattern.compile(regex, PATTERN_FLAGS);
    }

    @Override
    public State<? extends String> parse(Cursor cur) {
        final var matcher = pattern.matcher(cur.in());
        // Define the start and end positions to be the current
        // parser offset and the end of the entire string.
        matcher.region(cur.pos(), cur.in().length());
        // See, if the pattern matches the input at the beginning
        // of the region as defined above.
        if (matcher.lookingAt()) {
            return State.of(
                    cur.positionAt(matcher.end()),
                    matcher.group()
            );
        }
        return State.error(cur, "Regex mismatch: " + pattern);
    }
}
