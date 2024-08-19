package com.github.fabiitch.spawner.groups;

import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.groups.components.EntityData;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.pools.SpawnerPools;
import com.github.fabiitch.spawner.query.ComponentMatcher;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.utils.collections.Tab;
import lombok.Setter;


public class ComponentGroup<C> implements ComponentListener<C>{
    private final Tab<C> components = new Tab<>();

    @Setter
    private SpawnerPools pools;

    private final ComponentMatcher<C> matcher;
    private final ComponentMapper<C> mapper;

    public ComponentGroup(ComponentMatcher<C> matcher, ComponentMapper<C> mapper) {
        this.matcher = matcher;
        this.mapper = mapper;
        mapper.addListener(this);
    }

    public void remove() {
        mapper.removeListener(this);
    }

    public void init() {
        SafeTab<C> safeTab = mapper.getAll();
        for (int i = 0; i < safeTab.size(); i++) {
            if(matcher.accept(i)){
                add(i, );
            }
        }
        for (EntityData<C> entityData : array) {
            if (matcher.accept(entityData.getEntityId(), entityData.getComponent())) {
                add(entityData.getEntityId(), entityData.getComponent());
            }
        }
    }

    private void add(int entityId, C component) {
        components.set(entityId, component);
        components.set(entityId, entityData);
    }

    private void remove(int entityId, EntityData<C> entityData) {
        components.remove(entityId);
        pools.free(entityData);
    }

    @Override
    public void onComponentAdd(int entityId, C component, int componentIndex) {
        if (matcher.accept(entityId, component)) {
            add(entityId, component);
        }
    }

    @Override
    public void onComponentRemove(int entityId, C component, int componentIndex) {
        if (components.has(entityId)) {
            components.remove(entityId);
        }
    }

    @Override
    public void onComponentUpdate(int entityId, C component, int componentIndex) {
        boolean accept = matcher.accept(entityId, component);
        boolean present = components.has(entityId);
        if (accept && entityData == null) {
            add(entityId, component);
        } else if (!accept && entityData != null) {
            remove(entityId, entityData);
        }
    }

}
