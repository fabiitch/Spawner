package com.github.fabiitch.spawner.groups;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.criteria.ComponentImpacted;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.query.ComponentFilter;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import com.github.fabiitch.spawner.utils.collections.SafeTab;

import java.util.Iterator;


public class ComponentGroup<C> implements ComponentListener<C>, ComponentImpacted, Iterable<C> {
    private final IntArray entities = new IntArray();
    private final SafeIntArray result = new SafeIntArray(entities);

    private final ComponentFilter<C> matcher;
    private final ComponentMapper<C> mapper;
    private final Iterator<C> iterator;

    public ComponentGroup(ComponentFilter<C> matcher, ComponentMapper<C> mapper) {
        this.matcher = matcher;
        this.mapper = mapper;
        mapper.addListener(this);
        init();
        iterator = new ComponentGroupIterator(mapper, entities);
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

    @Override
    public Iterator<C> iterator() {
        return iterator;
    }

    @Override
    public boolean impactedByComponent(int indexComponent) {
        return mapper.impactedByComponent(indexComponent);
    }

    private class ComponentGroupIterator implements Iterator<C> {
        ComponentMapper<C> mapper;
        IntArray entities;
        int index;

        public ComponentGroupIterator(ComponentMapper<C> mapper, IntArray entities) {
            this.mapper = mapper;
            this.entities = entities;
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = index < entities.size;
            if (!hasNext)
                index = 0;
            return hasNext;
        }

        @Override
        public C next() {
            index++;
            return mapper.getComponent(entities.get(index));
        }
    }
}
