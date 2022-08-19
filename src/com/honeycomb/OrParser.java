package com.honeycomb;

/**
 * A {@link Parser} that accepts two {@link Parser}s {@code tis} and
 * {@code tat}. {@link Parser} {@code tat} is only invoked if and only
 * if {@code tis} {@link Parser} did not match.
 *
 * @param <T> the type of the parsed result value
 */
public final class OrParser<T> implements Parser<T> {

    private final Parser<T> tis;
    private final Parser<? extends T> tat;

    /**
     * Creates a new {@link Parser} that applies {@link Parser}
     * {@code tis} and if it fails {@link Parser} {@code tat}.
     *
     * @param tis the {@link Parser} to invoke first
     * @param tat the alternative {@link Parser}
     */
    public OrParser(Parser<T> tis, Parser<? extends T> tat) {
        this.tis = tis;
        this.tat = tat;
    }

    @Override
    public State<? extends T> parse(Cursor cur) {
        final var s = tis.parse(cur);
        if (s.isValid()) {
            return s;
        }
        return tat.parse(cur);
    }
}
