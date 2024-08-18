package com.github.fabiitch.spawner.groups;

import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.groups.components.EntityData;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.pools.SpawnerPools;
import com.github.fabiitch.spawner.query.ComponentMatcher;
import com.github.fabiitch.spawner.signals.SignalData;
import com.github.fabiitch.spawner.signals.SignalListener;
import com.github.fabiitch.spawner.utils.collections.Tab;
import lombok.Setter;


public class ComponentGroup<C extends SignalData> implements ComponentListener<C>, SignalListener<C> {

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
    public void init(){
        for (C component : mapper.getAll()) {
            if (matcher.accept(entityId, component)) {
                add(entityId, component);
            }
        }

    }

    private void add(int entityId, C component) {
        EntityData<C> entityData = pools.getEntityComponent();
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
    public void onUpdate(int entityId, C signalData) {
        boolean accept = matcher.accept(entityId, signalData);
        EntityData<C> entityData = tab.get(entityId);
        if (accept && entityData == null) {
            add(entityId, signalData);
        } else if (!accept && entityData != null) {
            remove(entityId, entityData);
        }
    }
}
