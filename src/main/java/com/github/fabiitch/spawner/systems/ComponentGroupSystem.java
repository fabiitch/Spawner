package com.github.fabiitch.spawner.systems;

import com.github.fabiitch.spawner.groups.ComponentGroup;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;

public abstract class ComponentGroupSystem<C> extends EngineSystem {
    private final ComponentGroup<C> group;

    public ComponentGroupSystem(ComponentGroup<C> group, int priority) {
        super(priority);
        this.group = group;
    }


    public abstract void updateEntity(int entityId, C component);

    @Override
    public void update(float dt) {
        SafeIntArray entities = group.getEntities();
        for (int i = 0, n = entities.size(); i < n; ++i) {
            int entityId = entities.get(i);
            updateEntity(entityId, group.getMapper().getComponent(entityId));
        }
    }
}
