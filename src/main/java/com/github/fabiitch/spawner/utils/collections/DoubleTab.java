package com.github.fabiitch.spawner.utils.collections;

import com.badlogic.gdx.utils.reflect.ArrayReflection;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Mainly used for stock behavior as [entityId][relativeComponentIndex]
 *
 * @param <T>
 */
public class DoubleTab<T> implements Iterable<T[]> {
    @Getter
    private T[][] items;

    private int[] sizesIndex;
    private int primaryNotNullSize;
    private int totalItems;

    @Getter
    @Setter
    private int preferedSizeIndex = 8;


    public DoubleTab() {
        this(16, 8);
    }

    public DoubleTab(int sizeFirst, int preferedSizeIndex) {
        this.items = (T[][]) new Object[sizeFirst][];
        this.sizesIndex = new int[sizeFirst];
        this.preferedSizeIndex = preferedSizeIndex;
    }

    public T getUnsafe(int indexFirst, int indexSecond) {
        return items[indexFirst][indexSecond];
    }

    public T[] getUnsafe(int indexFirst) {
        return items[indexFirst];
    }

    public T get(int indexFirst, int indexSecond) {
        T[] itemsIndex = get(indexFirst);
        if (indexSecond >= itemsIndex.length)
            return null;
        return itemsIndex[indexSecond];
    }

    public T[] get(int indexFirst) {
        if (indexFirst >= items.length)
            return null;
        return items[indexFirst];
    }


    public void set(int indexFirst, int indexSecond, T data) {
        if (data != null) {
            if (indexFirst >= items.length) {
                growPrimary();
            }
            T[] itemsIndex = items[indexFirst];
            if (itemsIndex == null) {
                int minSize = Math.max(preferedSizeIndex, indexSecond + 1);
                items[indexFirst] = itemsIndex = (T[]) new Object[minSize];
            } else if (indexSecond >= itemsIndex.length) {
                growIndex(indexFirst);
            }
            T itemActual = itemsIndex[indexSecond];
            if (itemActual == null) {
                totalItems++;
                int newSize = ++sizesIndex[indexFirst];
                if (newSize == 1)
                    primaryNotNullSize++;
            }
            itemsIndex[indexSecond] = data;
        }
    }

    public T remove(int indexFirst, int indexSecond) {
        if (indexFirst < items.length) {
            T[] itemIndex = items[indexFirst];
            if (itemIndex != null && indexSecond < itemIndex.length) {
                T remove = itemIndex[indexSecond];
                itemIndex[indexSecond] = null;
                int newSize = --sizesIndex[indexFirst];
                if (newSize == 0) {
                    primaryNotNullSize--;
                }
                return remove;
            }
        }
        return null;
    }

    public boolean has(int index, int indexSecond) {
        return get(index, indexSecond) != null;
    }

    public boolean has(int index) {
        return index < sizesIndex.length && sizesIndex[index] > 0;
    }

    public int totalSize() {
        return totalItems;
    }

    public int size() {
        return primaryNotNullSize;
    }

    public int size(int index) {
        if (index < sizesIndex.length)
            return 0;
        return sizesIndex[index];
    }

    public boolean isEmpty() {
        return size() > 0;
    }

    public boolean isEmpty(int index) {
        return size(index) > 0;
    }

    public int totalLength() {
        return items.length;
    }

    public int totalLength(int index) {
        T[] itemIndex = items[index];
        if (itemIndex == null)
            return 0;
        return itemIndex.length;
    }

    private void growPrimary() {
        int newCapacity = (items.length * 3) / 2 + 1;
        resizePrimary(newCapacity);
    }

    private void growIndex(int index) {
        int newCapacity = (items[index].length * 3) / 2 + 1;
        resizeIndex(index, newCapacity);
    }

    private int maxLength() {
        int max = 0;
        for (T[] item : items) {
            if (item != null)
                max = Math.max(max, item.length);
        }
        return max;
    }

    public void clear() {
        Arrays.fill(items, null);
    }

    protected T[][] resizePrimary(int newSize) {
        this.items = Arrays.copyOf(this.items, newSize);
        this.sizesIndex = Arrays.copyOf(this.sizesIndex, newSize);
        return items;
    }

    protected T[] resizeIndex(int indexFirst, int newSize) {
        T[] itemsIndex = this.items[indexFirst];
        T[] newItems = (T[]) ArrayReflection.newInstance(itemsIndex.getClass().getComponentType(), newSize);
        System.arraycopy(itemsIndex, 0, newItems, 0, Math.min(itemsIndex.length, newItems.length));
        this.items[indexFirst] = newItems;
        return newItems;
    }

    @Override
    public Iterator<T[]> iterator() {
        return null;

    }

    static public class DoubleTabIterator<T> implements Iterator<T> {
        private final DoubleTab<T> doubleTab;
        int indexPrimary;
        int indexSecond;

        int notNullItemsCount;
        int notNullCountPrimary;
        int notNullCountSecond;

        public DoubleTabIterator(DoubleTab<T> doubleTab) {
            this.doubleTab = doubleTab;
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = indexPrimary < doubleTab.totalLength() && notNullCountPrimary < doubleTab.size();//check primary

            if (!hasNext) { // if ok check secondary
                hasNext = indexSecond < doubleTab.totalLength(indexPrimary) && notNullCountSecond < doubleTab.size(indexPrimary);
            }
            if (hasNext) //check total length
                hasNext = notNullItemsCount < doubleTab.totalSize();

            if (!hasNext) {
                indexPrimary = 0;
                indexSecond = 0;
            }
            return hasNext;
        }

        @Override
        public T next() {
            T item = null;
            T[] itemIndex = null;
            indexSecond++;

            if (indexSecond == doubleTab.size(indexPrimary)) {
                indexSecond = 0;
                indexPrimary++;
            }
            itemIndex = doubleTab.items[indexPrimary];
            if (itemIndex == null)
                indexPrimary++;
            return null;
        }

    }
}
