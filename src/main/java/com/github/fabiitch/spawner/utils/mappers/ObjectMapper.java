package com.github.fabiitch.spawner.utils.mappers;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.utils.collections.Tab;

public abstract class ObjectMapper<T, L> extends BaseMapper<L> {
    protected final Tab<T> data = new Tab<>(16);

    public ObjectMapper(int index) {
        super(index);
    }

    public SafeTab<T> getAll() {
        return new SafeTab<>(data);
    }

    public IntArray getEntities(IntArray res) {
        return data.getIndexes(res);
    }
}
