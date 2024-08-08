package com.github.fabiitch.spawner.utils.collections;

import lombok.Getter;
import lombok.Setter;

import java.util.AbstractList;

@Getter
@Setter
public class IntList extends AbstractList<Integer> {

    private int[] values;

    @Override
    public Integer get(int index) {
        return values[index];
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public Integer set(int index, Integer element) {
        return values[index] = element;
    }
}
