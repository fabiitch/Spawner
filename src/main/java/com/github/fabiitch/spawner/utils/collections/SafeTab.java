package com.github.fabiitch.spawner.utils.collections;

import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class SafeTab<T> implements Iterable<T> {
    private final Tab<T> tab;
    private final Tab.TabIterator<T> iterator;

    public SafeTab(Tab<T> tab) {
        this.tab = tab;
        iterator = new Tab.TabIterator<>(tab);
    }

    public Array<T> getArray(Array<T> array) {
        tab.toArray(array);
        return array;
    }

    public T get(int index) {
        return tab.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

    public int size() {
        return tab.size();
    }

    public boolean isEmpty() {
        return tab == null || tab.isEmpty();
    }

    public T getFirst() {
        return tab.getFirst();
    }

    public T getLast() {
        return tab.getLast();
    }
}
