package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.function.Function;

public class MapParser<T, U> implements Parser<U> {

    private final Parser<T> parser;
    private final Function<T, U> mapping;

    public MapParser(Parser<T> parser, Function<T, U> mapping) {
        this.parser = parser;
        this.mapping = mapping;
    }

    @Override
    public <S> State<U> parse(State<S> state, String value) {
        return parser.parse(state, value).map(mapping);
    }

    @Override
    public <V> Parser<V> map(Function<U, V> mapping) {
        return new MapParser<>(this, mapping);
    }
}
