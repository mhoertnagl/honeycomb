package com.honeycomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.honeycomb.Prelude.Fun2;

/**
 * State of the parser. It holds the input {@link Cursor} and the parsed
 * result value, usually some sort of AST structure.
 *
 * @param <T> the type of the parsed result value
 */
public abstract class State<T> {

    /**
     * Creates a new parser {@link State} from an input {@link Cursor}
     * and a result value.
     *
     * @param cur the input {@link Cursor}
     * @param val the result value
     * @return a new parser {@link State}
     * @param <U> the type of the parsed result value
     */
    public static <U> State<U> of(Cursor cur, U val) {
        return new Valid<>(cur, val);
    }

    public static <U> State<U> error(Cursor cur, String error) {
        final var errors = new ArrayList<String>();
        errors.add(error);
        return error(cur, errors);
    }

    public static <U> State<U> error(Cursor cur, List<String> error) {
        return new Error<>(cur, error);
    }

    public abstract <U> State<U> flatMap(Fun2<Cursor, ? super T, State<U>> mapping);

    /**
     * Maps the result value of this parser {@link State} to a new state.
     *
     * @param mapping the result value mapping
     * @return a new parser {@link State} with te mapped result value
     * @param <U> the type of the mapped result value
     */
    public abstract <U> State<U> map(Function<? super T, ? extends U> mapping);

//    public abstract State<T> or(Supplier<State<? extends T>> alternative);

    public abstract boolean isValid();

    public abstract Optional<Cursor> cur();

    public abstract Optional<T> val();

    public abstract List<String> errors();

    /**
     * State of the parser. It holds the input {@link Cursor} and the parsed
     * result value, usually some sort of AST structure.
     *
     * @param <T> the type of the parsed result value
     */
    public static final class Valid<T> extends State<T> {

        private final Cursor cur;
        private final T val;

        /**
         * State of the parser. It holds the input {@link Cursor} and the parsed
         * result value, usually some sort of AST structure.
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
        public List<String> errors() {
            return new ArrayList<>();
        }
    }
    
    public static final class Error<T> extends State<T> {

        private final Cursor cur;
        private final List<String> errors;

        private Error(Cursor cur, List<String> errors) {
            this.cur = cur;
            this.errors = errors;
        }

        @Override
        public <U> State<U> flatMap(Fun2<Cursor, ? super T, State<U>> mapping) {
            return State.error(cur, errors);
        }

        @Override
        public <U> State<U> map(Function<? super T, ? extends U> mapping) {
            return State.error(cur, errors);
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
        public List<String> errors() {
            return errors;
        }
    }

    public record ErrorMessage()
}
