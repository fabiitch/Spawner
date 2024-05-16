package com.github.fabiitch.spawner.utils.pools;

import com.badlogic.gdx.utils.Pool;
import com.github.fabiitch.spawner.entity.Prototype;

public class PrototypePool extends Pool<Prototype> {
    @Override
    protected Prototype newObject() {
        return new Prototype();
    }

    @Override
    public void reset(Prototype prototype) {
        prototype.reset();
    }
}
