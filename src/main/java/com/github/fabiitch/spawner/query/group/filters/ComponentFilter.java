package com.github.fabiitch.spawner.query.group.filters;

public interface ComponentFilter<T> {

    boolean accept(T component);
}
