package com.honeycomb.parsers;

import com.honeycomb.State;

import java.util.function.Function;

public class MapParser<T, U> implements Parser<U> {

    private final Parser<T> parser;
    private final Function<? super T, ? extends U> mapping;

    public MapParser(Parser<T> parser, Function<? super T, ? extends U> mapping) {
        this.parser = parser;
        this.mapping = mapping;
    }

    @Override
    public <S> State<U> parse(State<S> state, String value) {
        return parser.parse(state, value).map(mapping);
    }
}
