package com.github.fabiitch.spawner.pools;

import com.badlogic.gdx.utils.Pools;
import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.groups.components.EntityData;

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

}
