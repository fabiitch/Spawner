package com.github.fabiitch.spawner.utils.collections;

import com.badlogic.gdx.utils.reflect.ArrayReflection;

import java.util.Iterator;

public class Tab<T> implements Iterable<T> {
    public T[] items;
    public int notNullSize;
    private final TabIterator<T> iterator;

    public Tab() {
        this(16);
    }

    public Tab(T[] items) {
        this.items = items;
        iterator = new TabIterator<>(this);
    }

    public Tab(int size) {
        items = (T[]) new Object[size];
        iterator = new TabIterator<>(this);
    }

    public T getUnsafe(int index) {
        return items[index];
    }

    public T get(int index) {
        if (index >= items.length)
            return null;
        return items[index];
    }

    public void set(int index, T data) {
        if (data != null) {
            if (index >= items.length) {
                grow();
            }
            items[index] = data;
            notNullSize++;
        }
    }

    public void setUnsafe(int index, T data) {
        items[index] = data;
    }

    public T remove(int index) {
        if (index < items.length) {
            T data = items[index];
            if (data != null) {
                items[index] = null;
                notNullSize--;
                return data;
            }
        }
        return null;
    }

    public void removeUnsafe(int index) {
        T data = items[index];
        items[index] = null;
        notNullSize--;
    }

    private void grow() {
        int newCapacity = (items.length * 3) / 2 + 1;
        resize(newCapacity);
    }

    protected T[] resize(int newSize) {
        T[] items = this.items;
        T[] newItems = (T[]) ArrayReflection.newInstance(items.getClass().getComponentType(), newSize);
        System.arraycopy(items, 0, newItems, 0, Math.min(items.length, newItems.length));
        this.items = newItems;
        return newItems;
    }

    public boolean isEmpty() {
        return notNullSize == 0;
    }

    public int size() {
        return notNullSize;
    }

    public int totalLenght() {
        return items.length;
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

    static public class TabIterator<T> implements Iterator<T> {
        private final Tab<T> tab;
        int index;
        int notNullCount;

        public TabIterator(Tab<T> tab) {
            this.tab = tab;
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = index < tab.totalLenght() && notNullCount <= tab.notNullSize;
            if (!hasNext)
                index = 0;
            return hasNext;
        }

        @Override
        public T next() {
            T item = tab.items[index];
            while (item == null)
                index++;
            return tab.items[index++];
        }

    }
}
