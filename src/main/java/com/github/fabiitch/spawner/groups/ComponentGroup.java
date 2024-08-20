package com.github.fabiitch.spawner.groups;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.query.ComponentMatcher;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import com.github.fabiitch.spawner.utils.collections.SafeTab;


public class ComponentGroup<C> implements ComponentListener<C> {
    private final IntArray entities = new IntArray();
    private final SafeIntArray result = new SafeIntArray(entities);

    private final ComponentMatcher<C> matcher;
    private final ComponentMapper<C> mapper;

    public ComponentGroup(ComponentMatcher<C> matcher, ComponentMapper<C> mapper) {
        this.matcher = matcher;
        this.mapper = mapper;
        mapper.addListener(this);
        init();
    }

    public void remove() {
        mapper.removeListener(this);
        entities.clear();
    }

    public SafeIntArray getEntities() {
        return result;
    }


    public void init() {
        SafeTab<C> safeTab = mapper.getAll();
        for (int entityId = 0; entityId < safeTab.size(); entityId++) {
            if (matcher.accept(entityId)) {
                entities.add(entityId);
            }
        }
    }

    @Override
    public void onComponentAdd(int entityId, C component, int componentIndex) {
        if (matcher.accept(entityId, component)) {
            entities.add(entityId);
        }
    }

    @Override
    public void onComponentRemove(int entityId, C component, int componentIndex) {
        entities.removeValue(entityId);
    }

    @Override
    public void onComponentUpdate(int entityId, C component, int componentIndex) {
        boolean accept = matcher.accept(entityId, component);
        boolean present = entities.contains(entityId);
        if (accept && !present) {
            entities.add(entityId);
        } else if (!accept && present) {
            entities.removeValue(entityId);
        }
    }
}
