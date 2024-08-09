package com.github.fabiitch.spawner.query;

import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;
import com.github.fabiitch.spawner.component.ComponentMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ComponentMatcher<T> implements EntityMatcher {

    private ComponentMapper<T> mapper;

    public abstract boolean accept(int entityId, final T component);

    @Override
    public boolean accept(int entityId) {
        return accept(entityId, mapper.getComponent(entityId));
    }
}
