package com.github.fabiitch.spawner.group.filters;

public interface ComponentFilter<T> {

    boolean accept(T component);
}
