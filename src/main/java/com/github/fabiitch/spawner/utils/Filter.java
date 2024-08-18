package com.github.fabiitch.spawner.utils;

public interface Filter<T> {

    boolean accept(T t);
}
