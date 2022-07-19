package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.function.Supplier;

public class RefParser<T> implements Parser<T> {

    private Supplier<Parser<T>> parser;

    public void set(Supplier<Parser<T>> parser) {
        this.parser = parser;
    }

    @Override
    public <S> State<? extends T> parse(State<S> state, String value) {
        return parser.get().parse(state, value);
    }
}
