package com.honeycomb;

import com.honeycomb.funs.Fun2;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class State<T> {

  public final int pos;
  public final int row;
  public final int col;

  private State(int pos, int row, int col) {
    this.pos = pos;
    this.row = row;
    this.col = col;
  }

  public static <T1,T2, R> R whenAll(
          State<T1> s1,
          State<T2> s2,
          Fun2<T1, T2, R> fun) {
    if (s1.isPresent() && s2.isPresent()) {
      fun.apply(s1.get(), s2.get());
    }
    return null;
  }

  public static <T> State<T> result(int pos, int row, int col, T val) {
    return new ResultState<>(pos, row, col, val);
  }

//  public State<T> result(int offset, T val) {
//    return result(pos + offset, row, col, val);
//  }

  public static <T> State<T> error(int pos, int row, int col, String format, Object... args) {
    return error(pos, row, col, String.format(format, args));
  }

  public static <T> State<T> error(int pos, int row, int col, String err) {
    return new ErrorState<>(pos, row, col, err);
  }

  public <U> State<U> error(String format, Object... args) {
    return error(pos, row, col, format, args);
  }

  public abstract <U> State<U> map(Function<? super T, ? extends U> mapping);

  public abstract State<T> or(Supplier<State<T>> supplier);

  public abstract T get();

  public abstract boolean isPresent();

  private static class ResultState<T> extends State<T> {

    public final T val;

    private ResultState(int pos, int row, int col, T val) {
      super(pos, row, col);
      this.val = val;
    }

    @Override
    public <U> State<U> map(Function<? super T, ? extends U> mapping) {
      return new ResultState<>(pos, row, col, mapping.apply(val));
    }

    @Override
    public State<T> or(Supplier<State<T>> supplier) {
      return this;
    }

    @Override
    public T get() {
      return val;
    }

    @Override
    public boolean isPresent() {
      return true;
    }
  }

  private static class ErrorState<T> extends State<T> {

    public final String err;

    private ErrorState(int pos, int row, int col, String err) {
      super(pos, row, col);
      this.err = err;
    }

    @Override
    public <U> State<U> map(Function<? super T, ? extends U> mapping) {
      return new ErrorState<>(pos, row, col, err);
    }

    @Override
    public State<T> or(Supplier<State<T>> supplier) {
      return supplier.get();
    }

    @Override
    public T get() {
      return null;
    }

    @Override
    public boolean isPresent() {
      return false;
    }
  }
}