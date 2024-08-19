package com.github.fabiitch.spawner.pools;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.groups.components.EntityData;
import com.github.fabiitch.spawner.utils.collections.SafeTab;

public class SpawnerGdxPools implements SpawnerPools {
    @Override
    public EntityReference getEntityReference() {
        return null;
    }

    @Override
    public void free(EntityReference entityReference) {
        Pools.free(entityReference);
    }

    @Override
    public <T> EntityData<T> getEntityComponent() {
        EntityData entityData = Pools.obtain(EntityData.class);
        return entityData;
    }

    @Override
    public void free(EntityData entityData) {
        Pools.free(entityData);
    }

    @Override
    public <T> Array<T> getArray() {
        return Pools.obtain(Array.class);
    }

    @Override
    public <T> void free(Array<T> array) {
        Pools.free(array);
    }

}
