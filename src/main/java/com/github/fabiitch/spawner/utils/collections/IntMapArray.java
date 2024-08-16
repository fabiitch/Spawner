package com.github.fabiitch.spawner.utils.collections;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

public class IntMapArray<T> {

    private final IntMap<Array<T>> intMap = new IntMap<>();


    public Array<T> get(int key) {
        return intMap.get(key);
    }

    T getUnsafe(int key, int index) {
        return get(key).get(index);
    }

    public T get(int key, int index) {
        Array<T> array = get(key);
        if (array == null)
            return null;

        if (index > array.size)
            return null;
        return array.get(index);
    }

    public void add(int key, T data) {
        Array<T> array = intMap.get(key);
        if (array == null) {
            array = new Array<>();
            intMap.put(key, array);
        }
        array.add(data);
    }

    public boolean remove(int key, T data) {
        Array<T> array = intMap.get(key);
        if (array != null) {
            return array.removeValue(data, true);
        }
        return false;
    }
}
