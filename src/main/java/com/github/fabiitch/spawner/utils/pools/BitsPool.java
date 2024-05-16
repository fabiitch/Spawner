package com.github.fabiitch.spawner.utils.pools;

import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.Pool;

public class BitsPool extends Pool<Bits> {
    @Override
    protected Bits newObject() {
        return new Bits();
    }

    @Override
    public void reset(Bits bits) {
        bits.clear();
    }
}
