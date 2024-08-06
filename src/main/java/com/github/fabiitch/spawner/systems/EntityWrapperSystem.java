package com.github.fabiitch.spawner.systems;

import com.github.fabiitch.spawner.entity.EntityWrapper;
import com.github.fabiitch.spawner.family.Family;

public abstract class EntityWrapperSystem<T extends EntityWrapper> extends EntitySystem {

    T entityWrapper;

    public EntityWrapperSystem(Family family, int priority) {
        super(family, priority);
    }

    @Override
    protected void processEntity(int entityId, float dt) {

    }

    public abstract void processEntity(T entity, float dt);
}
