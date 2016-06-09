package com.baeldung.collectors;

import com.baeldung.Employee;
import com.google.common.collect.ImmutableMap;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ImmutableMapCollector<T extends Employee, K extends Number, A extends ImmutableMap.Builder<K, T>, R extends ImmutableMap<K, T>> implements Collector<T, ImmutableMap.Builder<K, T>, ImmutableMap<K, T>> {

    private Function<T, K> keyMapper;

    public ImmutableMapCollector() {
    }

    public void setUp(Function<T, K> keyMapper) {
        this.keyMapper = keyMapper;
    }

    @Override
    public Supplier<ImmutableMap.Builder<K, T>> supplier() {
        return ImmutableMap::builder;
    }

    @Override
    public BiConsumer<ImmutableMap.Builder<K, T>, T> accumulator() {
        return (map, value) -> map.put(keyMapper.apply(value), value);
    }

    @Override
    public BinaryOperator<ImmutableMap.Builder<K, T>> combiner() {
        return (c1, c2) -> {
            c1.putAll(c2.build());
            return c1;
        };
    }

    @Override
    public Function<ImmutableMap.Builder<K, T>, ImmutableMap<K, T>> finisher() {
        return ImmutableMap.Builder::build;
    }

    @Override
    public Set<java.util.stream.Collector.Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }
}
