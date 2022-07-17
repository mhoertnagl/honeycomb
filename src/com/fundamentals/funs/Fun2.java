package com.fundamentals.funs;

@FunctionalInterface
public interface Fun2<T1, T2, R> {
    R apply(T1 t1, T2 t2);
}
