package com.github.fabiitch.spawner.query.matchers;

import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;
import com.github.fabiitch.spawner.flag.FlagMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class FlagMatcher<T> implements EntityMatcher {

    private final FlagMapper mapper;

    public abstract boolean accept(int entityId, boolean hasFlag);

    @Override
    public boolean accept(int entityId) {
        return accept(entityId, mapper.hasFlag(entityId));
    }
}
