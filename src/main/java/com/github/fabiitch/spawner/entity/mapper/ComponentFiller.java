package com.github.fabiitch.spawner.entity.mapper;

import com.github.fabiitch.spawner.component.ComponentMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class ComponentFiller<W, C> {

     @Getter
     private ComponentMapper<C> mapper;

     public abstract void fill(W entityWrappper);
}
