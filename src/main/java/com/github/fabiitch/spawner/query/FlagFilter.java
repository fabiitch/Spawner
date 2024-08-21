package com.github.fabiitch.spawner.query;

import com.github.fabiitch.spawner.impact.FlagImpacted;
import com.github.fabiitch.spawner.flag.FlagMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class FlagFilter<T> implements EntityFilter, FlagImpacted {

    private final FlagMapper mapper;

    public abstract boolean accept(int entityId, boolean hasFlag);

    @Override
    public boolean accept(int entityId) {
        return accept(entityId, mapper.hasFlag(entityId));
    }

    @Override
    public boolean impactedByFlag(int indexFlag) {
        return indexFlag == mapper.getIndex();
    }
}
