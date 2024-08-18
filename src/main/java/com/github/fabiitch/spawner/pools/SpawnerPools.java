package com.github.fabiitch.spawner.pools;

import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.groups.components.EntityData;
import com.github.fabiitch.spawner.utils.collections.SafeTab;

public interface SpawnerPools {

    EntityReference getEntityReference();

    void free(EntityReference entityReference);


    <T> EntityData<T> getEntityComponent();

    void free(EntityData entityData);
}
