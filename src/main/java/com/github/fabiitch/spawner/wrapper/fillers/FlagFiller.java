package com.github.fabiitch.spawner.wrapper.fillers;

import com.github.fabiitch.spawner.wrapper.EntityWrapper;
import com.github.fabiitch.spawner.flag.FlagMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class FlagFiller<W extends EntityWrapper> {
    private FlagMapper mapper;

    public void fill(W entityWrappper, int entityId){
        fill(entityWrappper, mapper.hasFlag(entityId));
    }

    public abstract void fill(W entityWrappper, boolean hasFlag);
}
