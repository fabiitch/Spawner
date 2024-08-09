package com.github.fabiitch.spawner.query.matchers.wrapper;

import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;
import com.github.fabiitch.spawner.entity.EntityWrapper;
import com.github.fabiitch.spawner.entity.mapper.EntityMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class EntityWrapperMatcher<W extends EntityWrapper> implements EntityMatcher {

    private EntityMapper<W> mapper;

    public abstract boolean accept(W entityWrapper);

    @Override
    public boolean accept(int entityId) {
        W wrapper = mapper.get(entityId);
        return accept(wrapper);
    }
}
