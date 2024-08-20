package com.github.fabiitch.spawner.query;

import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;
import com.github.fabiitch.spawner.archetype.criteria.FlagImpacted;
import com.github.fabiitch.spawner.flag.FlagMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class FlagMatcher<T> implements EntityMatcher, FlagImpacted {

    private final FlagMapper mapper;

    public abstract boolean accept(int entityId, boolean hasFlag);

    @Override
    public boolean accept(int entityId) {
        return accept(entityId, mapper.hasFlag(entityId));
    }

    @Override
    public boolean impactedByFlag(int flagIndex) {
        return flagIndex == mapper.getIndex();
    }
}
