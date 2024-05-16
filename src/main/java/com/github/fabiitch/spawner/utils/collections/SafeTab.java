package com.github.fabiitch.spawner.utils.collections;

import java.util.Iterator;

public class SafeTab<T> implements Iterable<T> {
    private final Tab<T> tab;
    private final Tab.TabIterator<T> iterator;

    public T get(int index) {
        return tab.get(index);
    }

    public SafeTab(Tab<T> tab) {
        this.tab = tab;
        iterator = new Tab.TabIterator<>(tab);
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

}
