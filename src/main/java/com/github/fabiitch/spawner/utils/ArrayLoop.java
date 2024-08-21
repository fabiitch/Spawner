package com.github.fabiitch.spawner.utils;

import com.badlogic.gdx.utils.Array;

public class ArrayLoop<T> {

    private boolean isLoop;
    private final Array<T> array;
    private int loopIndex = 0;

    public ArrayLoop(Array<T> array) {
        this.array = array;
    }

    public void add(T t) {
        array.add(t);
    }

    public boolean remove(T t, boolean identity) {
        if (isLoop) {
            int index = array.lastIndexOf(t, true);
            if (index >= loopIndex) {
                array.removeIndex(index);
                loopIndex--;
                return true;
            } else {
                return false;
            }
        } else
            return array.removeValue(t, identity);

    }

    public T loop() {
        if (loopIndex >= array.size) {
            reset();
            return null;
        }
        T t = array.get(loopIndex);
        loopIndex++;
        return t;
    }

    public ArrayLoop<T> reset() {
        loopIndex = 0;
        isLoop = false;
        return this;
    }
}
