package com.fundamentals;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Either<L,R> {

    public static <L,R> Either<L,R> left(L value) {
        return new Left<>(value);
    }

    public static <L,R> Either<L,R> right(R value) {
        return new Right<>(value);
    }

    public abstract <L2> Either<L2,R> mapLeft(Function<L,L2> mapping);

    public abstract <R2> Either<L,R2> mapRight(Function<R,R2> mapping);

    public abstract <L2> Either<L2,R> flatMapLeft(Function<L, Either<L2,R>> mapping);

    public abstract <R2> Either<L,R2> flatMapRight(Function<R, Either<L,R2>> mapping);

    public abstract  Either<L,R> leftOrElse(Supplier<Either<L,R>> supplier);

    public abstract  Either<L,R> rightOrElse(Supplier<Either<L,R>> supplier);

    public abstract L getLeftOrElse(Supplier<L> supplier);

    public abstract R getRightOrElse(Supplier<R> supplier);

    private static class Left<L,R> extends Either<L,R> {

        private final L value;

        private Left(L value) { this.value = value; }

        public <L2> Either<L2,R> mapLeft(Function<L,L2> mapping) {
            return new Left<>(mapping.apply(value));
        }

        public <R2> Either<L,R2> mapRight(Function<R,R2> mapping) {
            return new Left<>(value);
        }

        public <L2> Either<L2,R> flatMapLeft(Function<L, Either<L2,R>> mapping) {
            return mapping.apply(value);
        }

        public <R2> Either<L,R2> flatMapRight(Function<R, Either<L,R2>> mapping) {
            return new Left<>(value);
        }

        public L getLeftOrElse(Supplier<L> supplier) {
            return value;
        }

        public R getRightOrElse(Supplier<R> supplier) {
            return supplier.get();
        }

        public Either<L, R> leftOrElse(Supplier<Either<L, R>> supplier) {
            return this;
        }

        public Either<L, R> rightOrElse(Supplier<Either<L, R>> supplier) {
            return supplier.get();
        }

        @Override
        public String toString() {
            return String.format("Left(%s)", value);
        }
    }

    private static class Right<L,R> extends Either<L,R> {

        private final R value;

        private Right(R value) { this.value = value; }

        public <L2> Either<L2,R> mapLeft(Function<L,L2> mapping) {
            return new Right<>(value);
        }

        public <R2> Either<L,R2> mapRight(Function<R,R2> mapping) {
            return new Right<>(mapping.apply(value));
        }

        public <L2> Either<L2,R> flatMapLeft(Function<L, Either<L2,R>> mapping) {
            return new Right<>(value);
        }

        public <R2> Either<L,R2> flatMapRight(Function<R, Either<L,R2>> mapping) {
            return mapping.apply(value);
        }

        public L getLeftOrElse(Supplier<L> supplier) {
            return supplier.get();
        }

        public R getRightOrElse(Supplier<R> supplier) {
            return value;
        }

        public Either<L, R> leftOrElse(Supplier<Either<L, R>> supplier) {
            return supplier.get();
        }

        public Either<L, R> rightOrElse(Supplier<Either<L, R>> supplier) {
            return this;
        }

        @Override
        public String toString() {
            return String.format("Right(%s)", value);
        }
    }
}
