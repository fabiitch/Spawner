package com.github.fabiitch.spawner.factory;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GdxPooledFactory<T> implements Factory<T>{
    private Class<T> factoryClass;

    @Override
    public T getNew() {
        return Pools.obtain(factoryClass);
    }

    @Override
    public void free(T object) {
        Pools.free(object);
    }
}
