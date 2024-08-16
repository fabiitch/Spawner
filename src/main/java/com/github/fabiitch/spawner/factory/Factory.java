package com.github.fabiitch.spawner.factory;

public interface Factory<T> {

    T getNew();

    void free(T object);

    Class<T> getFactoryClass();
}
