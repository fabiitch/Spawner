package com.github.fabiitch.spawner.query.matchers.wrapper;

import com.github.fabiitch.spawner.query.EntityFilter;
import com.github.fabiitch.spawner.wrapper.EntityWrapper;
import com.github.fabiitch.spawner.wrapper.EntityMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class EntityWrapperFilter<W extends EntityWrapper> implements EntityFilter {

    private EntityMapper<W> mapper;

    public abstract boolean accept(W entityWrapper);

    @Override
    public boolean accept(int entityId) {
        W wrapper = mapper.get(entityId);
        return accept(wrapper);
    }
}
