package com.github.fabiitch.spawner.utils.collections;

import java.util.Iterator;

public class DoubleSafeTab<T> implements Iterable<T> {

    private final DoubleTab<T> doubleTab;
    private final Tab<SafeTab<T>> safeTabs;

    public DoubleSafeTab(DoubleTab<T> doubleTab) {
        this.doubleTab = doubleTab;
        this.safeTabs = new Tab<>(doubleTab.totalLength());
    }

    public SafeTab<T> get(int indexPrimary) {
        return null;
    }

    public T get(int first, int second) {
        return null;
    }


    public boolean has(int index) {
        return get(index) != null;
    }

    public boolean has(int index, int second) {
        return get(index, second) != null;
    }

    public int size() {
        return doubleTab.size();
    }

    public int size(int index) {
        return doubleTab.size(index);
    }

    public boolean isEmpty() {
        return doubleTab.isEmpty();
    }

    public boolean isEmpty(int index) {
        return doubleTab.isEmpty(index);
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
