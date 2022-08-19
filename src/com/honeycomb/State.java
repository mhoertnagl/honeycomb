package com.honeycomb;

import java.util.Optional;
import java.util.function.Function;

import com.honeycomb.Prelude.Fun2;

/**
 * State of the parser. It can be either {@link Valid} or {@link Error}.
 *
 * @param <T> the type of the parsed result value
 */
public abstract class State<T> {

    /**
     * Creates a new parser {@link State.Valid} from an input {@link Cursor}
     * and a result value.
     *
     * @param cur the input {@link Cursor}
     * @param val the result value
     * @return a new parser {@link State.Valid}
     * @param <U> the type of the parsed result value
     */
    public static <U> State<U> of(Cursor cur, U val) {
        return new Valid<>(cur, val);
    }

    /**
     * Creates a new parser {@link State.Error} from an input {@link Cursor}
     * and an error {@code message} string.
     *
     * @param cur the input {@link Cursor}
     * @param message the error {@code message} string
     * @return a new parser {@link State.Error}
     * @param <U> the type of the parsed result value
     */
    public static <U> State<U> error(Cursor cur, String message) {
        final var line = cur.getLineNo();
        final var col = cur.getCharPos();
        return error(cur, new ErrorMessage(line, col, message));
    }

    /**
     * Creates a new parser {@link State.Error} from an input {@link Cursor}
     * and an {@link ErrorMessage}.
     *
     * @param cur the input {@link Cursor}
     * @param error the {@link ErrorMessage}
     * @return a new parser {@link State.Error}
     * @param <U> the type of the parsed result value
     */
    public static <U> State<U> error(Cursor cur, ErrorMessage error) {
        return new Error<>(cur, error);
    }

    /**
     * Maps the result value of this parser {@link State} to a new state.
     *
     * @param mapping the result value mapping
     * @return a new parser {@link State} with te mapped result state
     * @param <U> the type of the mapped result value
     */
    public abstract <U> State<U> flatMap(Fun2<Cursor, ? super T, State<U>> mapping);

    /**
     * Maps the result value of this parser {@link State} to a new value.
     *
     * @param mapping the result value mapping
     * @return a new parser {@link State} with te mapped result value
     * @param <U> the type of the mapped result value
     */
    public abstract <U> State<U> map(Function<? super T, ? extends U> mapping);

//    public abstract State<T> or(Supplier<State<? extends T>> alternative);

    /**
     * Returns true iff this {@link State} is a {@link Valid} state.
     *
     * @return true iff a {@link Valid} state.
     */
    public abstract boolean isValid();

    /**
     * Returns the {@link Cursor}.
     *
     * @return the {@link Cursor}
     */
    public abstract Optional<Cursor> cur();

    /**
     * Returns the value.
     *
     * @return the value
     */
    public abstract Optional<T> val();

    /**
     * Returns the {@link ErrorMessage}.
     *
     * @return the {@link ErrorMessage}
     */
    public abstract Optional<ErrorMessage> error();

    /**
     * Valid state of the parser. It holds the input {@link Cursor} and the
     * parsed result value, usually some sort of AST structure.
     *
     * @param <T> the type of the parsed result value
     */
    public static final class Valid<T> extends State<T> {

        private final Cursor cur;
        private final T val;

        /**
         * Creates a new valid state.
         *
         * @param cur the input {@link Cursor}
         * @param val the parser result value
         */
        private Valid(Cursor cur, T val) {
            this.cur = cur;
            this.val = val;
        }

        @Override
        public <U> State<U> flatMap(Fun2<Cursor, ? super T, State<U>> mapping)     {
            return mapping.apply(cur, val);
        }

        @Override
        public <U> State<U> map(Function<? super T, ? extends U> mapping) {
            return State.of(cur, mapping.apply(val));
        }

//        @Override
//        public State<T> or(Supplier<State<? extends T>> alternative) {
//            return this;
//        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public Optional<Cursor> cur() {
            return Optional.of(cur);
        }

        @Override
        public Optional<T> val() {
            return Optional.of(val);
        }

        @Override
        public Optional<ErrorMessage> error() {
            return Optional.empty();
        }
    }

    /**
     * Error state of the parser. It holds the input {@link Cursor} and an
     * error message.
     *
     * @param <T> the type of the parsed result value
     */
    public static final class Error<T> extends State<T> {

        private final Cursor cur;
        private final ErrorMessage error;

        /**
         * Creates a new error state.
         *
         * @param cur the input {@link Cursor}
         * @param error the {@link ErrorMessage}
         */
        private Error(Cursor cur, ErrorMessage error) {
            this.cur = cur;
            this.error = error;
        }

        @Override
        public <U> State<U> flatMap(Fun2<Cursor, ? super T, State<U>> mapping) {
            return State.error(cur, error);
        }

        @Override
        public <U> State<U> map(Function<? super T, ? extends U> mapping) {
            return State.error(cur, error);
        }

//        @Override
//        @SuppressWarnings("unchecked")
//        public State<T> or(Supplier<State<? extends T>> alternative) {
//            return (State<T>) alternative.get();
//        }

        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        public Optional<Cursor> cur() {
            return Optional.empty();
        }

        @Override
        public Optional<T> val() {
            return Optional.empty();
        }

        @Override
        public Optional<ErrorMessage> error() {
            return Optional.of(error);
        }
    }

    /**
     * An error message with additional positional information.
     *
     * @param line the line number
     * @param col the character position within {@code line}
     * @param message the error {@code message} string
     */
    public record ErrorMessage(int line, int col, String message) {

        @Override
        public String toString() {
            return String.format("%d:%d ERROR: %s", line, col, message);
        }
    }
}
