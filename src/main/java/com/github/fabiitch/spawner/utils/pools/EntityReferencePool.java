package com.github.fabiitch.spawner.utils.pools;

import com.badlogic.gdx.utils.Pool;
import com.github.fabiitch.spawner.entity.EntityReference;

public class EntityReferencePool extends Pool<EntityReference> {
    @Override
    protected EntityReference newObject() {
        return new EntityReference();
    }

    @Override
    public void reset(EntityReference entityReference) {
        entityReference.reset();
    }
}
