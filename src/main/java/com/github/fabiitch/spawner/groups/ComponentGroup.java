package com.github.fabiitch.spawner.groups;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.groups.components.EntityData;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.pools.SpawnerPools;
import com.github.fabiitch.spawner.query.ComponentMatcher;
import com.github.fabiitch.spawner.utils.collections.Tab;
import lombok.Setter;


public class ComponentGroup<C> implements ComponentListener<C>{
    private final Tab<EntityData<C>> tab = new Tab<>();

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
        Array<EntityData<C>> array = mapper.getAll(pools.getArray());//TODO pools
        for (EntityData<C> entityData : array) {
            if (matcher.accept(entityData.getEntityId(), entityData.getComponent())) {
                add(entityData.getEntityId(), entityData.getComponent());
            }
        }
    }

    private void add(int entityId, C component) {
        EntityData<C> entityData = pools.getEntityComponent(); //TODO pools
        entityData.set(entityId, component);
        tab.set(entityId, entityData);
    }

    private void remove(int entityId, EntityData<C> entityData) {
        tab.remove(entityId);
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
        EntityData<C> entityData = tab.get(entityId);
        if (entityData != null) {
            remove(entityId, entityData);
        }
    }

    @Override
    public void onComponentUpdate(int entityId, C component, int componentIndex) {
        boolean accept = matcher.accept(entityId, component);
        EntityData<C> entityData = tab.get(entityId);
        if (accept && entityData == null) {
            add(entityId, component);
        } else if (!accept && entityData != null) {
            remove(entityId, entityData);
        }
    }

}
