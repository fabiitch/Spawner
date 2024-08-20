package com.github.fabiitch.spawner.query;

import com.github.fabiitch.spawner.archetype.criteria.BehaviorImpacted;
import com.github.fabiitch.spawner.behavior.BehaviorMapper;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BehaviorFilter<T> implements EntityFilter, BehaviorImpacted {

    private BehaviorMapper<T> mapper;

    public abstract boolean accept(int entityId, final T behavior);

    @Override
    public boolean impactedByBehavior(int behaviorIndex) {
        return behaviorIndex == mapper.getIndex();
    }

    @Override
    public boolean accept(int entityId) {
        SafeTab<T> behaviors = mapper.getBehaviors(entityId);
        if(behaviors == null) return false;
        for (T behavior : behaviors) {
            if(accept(entityId, behavior))
                return true;
        }
        return false;
    }
}
