package com.github.fabiitch.spawner.wrapper.fillers;

import com.github.fabiitch.spawner.aspect.AspectMapper;
import com.github.fabiitch.spawner.wrapper.EntityWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AspectFiller<W extends EntityWrapper, A> {

    private AspectMapper<A> mapper;

    public void fill(W entityWrappper, int entityId) {
        fill(entityWrappper, mapper.getAspect(entityId));
    }

    public abstract void fill(W entityWrappper, A aspect);
}
