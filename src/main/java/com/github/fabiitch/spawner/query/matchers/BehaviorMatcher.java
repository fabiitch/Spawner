package com.github.fabiitch.spawner.query.matchers;

import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;
import com.github.fabiitch.spawner.behavior.BehaviorMapper;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BehaviorMatcher<B> implements EntityMatcher {

    private final BehaviorMapper<B> mapper;

    public abstract boolean accept(int entityId, final SafeTab<B> behaviorTab);

    @Override
    public boolean accept(int entityId) {
        return accept(entityId, mapper.getBehaviors(entityId));
    }
}
