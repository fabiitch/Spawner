package com.github.fabiitch.spawner.entity.mapper.fillers;

import com.github.fabiitch.spawner.behavior.BehaviorMapper;
import com.github.fabiitch.spawner.entity.EntityWrapper;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.utils.collections.Tab;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BehaviorFiller<W extends EntityWrapper, B> {
    private BehaviorMapper<B> mapper;

    public void fill(W entityWrappper, int entityId) {
        fill(entityWrappper, mapper.getBehaviors(entityId));
    }

    public abstract void fill(W entityWrappper, SafeTab<B> behaviors);
}

