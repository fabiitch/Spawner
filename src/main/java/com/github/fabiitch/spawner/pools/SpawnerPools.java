package com.github.fabiitch.spawner.pools;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.entity.EntityReference;

public interface SpawnerPools {

    EntityReference getEntityReference();

    void free(EntityReference entityReference);


    <T> Array<T> getArray();

    <T> void free(Array<T> array);
}
