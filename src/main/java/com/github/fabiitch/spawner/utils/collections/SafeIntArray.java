package com.github.fabiitch.spawner.utils.collections;

import com.badlogic.gdx.utils.IntArray;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SafeIntArray {

    private final IntArray array;

    public IntArray cpy(){
        return new IntArray(array);
    }

    public int size(){
        return array.size;
    }

    public boolean contains(int value){
        return array.contains(value);
    }

    public int indexOf(int value) {
        return array.indexOf(value);
    }

    public int lastIndexOf(int value) {
        return array.lastIndexOf(value);
    }

    public int first() {
        return array.first();
    }

    public int[] toArray() {
        return array.toArray();
    }

    public int get(int index) {
        return array.get(index);
    }
}
