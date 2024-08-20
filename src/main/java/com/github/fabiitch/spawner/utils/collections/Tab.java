package com.github.fabiitch.spawner.utils.collections;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import lombok.Getter;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Array who keep always value at index inserted
 * Many times the index is the entityId
 * NON Thread safe object on iteration
 *
 * @param <T>
 */
public class Tab<T> implements Iterable<T> {
    @Getter
    private T[] items;
    private int notNullSize;
    @Getter
    private final TabIterator<T> iterator;

    public Tab() {
        this(16);
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
            if (items[index] == null)
                notNullSize++;
            items[index] = data;
        }
    }

    public boolean has(int index) {
        return get(index) != null;
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

    public T getFirst() {
        for (T item : items) {
            if (item != null)
                return item;
        }
        return null;
    }

    public T getLast() {
        for (int i = items.length; i > 0; i--) {
            if (items[i] != null)
                return items[i];
        }
        return null;
    }

    public Array<T> toArray(Array<T> res) {
        res.setSize(this.size());
        for (int i = 0; i < items.length; i++) {
            T item = items[i];
            if (item != null)
                res.add(item);
        }
        return res;
    }

    public void clear() {
        Arrays.fill(items, null);
    }

    public int indexOf(T t) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == t)
                return i;
        }
        return -1;
    }

    public IntArray getIndexes(IntArray res) {
        for (int i = 0; i < items.length; i++) {
            T item = items[i];
            if (item != null)
                res.add(i);
        }
        return res;
    }

    public boolean isEmpty() {
        return notNullSize == 0;
    }

    public int size() {
        return notNullSize;
    }

    public int totalLength() {
        return items.length;
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }

    static public class TabIterator<T> implements Iterator<T> {
        private final Tab<T> tab;
        private int index;
        private int notNullCount;

        public TabIterator(Tab<T> tab) {
            this.tab = tab;
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = index < tab.totalLength() && notNullCount < tab.size();
            if (!hasNext)
                index = 0;
            return hasNext;
        }

        @Override
        public T next() {
            T item = null;
            int maxLenght = tab.totalLength();
            while (item == null && index < maxLenght) {
                index++;
                item = tab.items[index];
            }
            notNullCount++;
            return item;
        }
    }
}
