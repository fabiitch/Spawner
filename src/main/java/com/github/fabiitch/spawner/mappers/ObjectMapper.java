package com.github.fabiitch.spawner.mappers;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.groups.components.EntityData;
import com.github.fabiitch.spawner.pools.SpawnerPools;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.utils.collections.Tab;

public abstract class ObjectMapper<T, L> extends BaseMapper<L> {
    protected final Tab<T> data = new Tab<>(16);

    public ObjectMapper(int index, SpawnerPools pools) {
        super(index);
    }

    public SafeTab<T> getAll() {
        return new SafeTab<>(data);
    }

    public Array<EntityData<T>> getAll(Array<EntityData<T>> res) {
        for (int i = 0; i < data.totalLength(); i++) {
            T unsafe = data.getUnsafe(i);
            if (unsafe != null)
                res.add(new EntityData<>(i, unsafe));
        }
        return res;
    }
}
