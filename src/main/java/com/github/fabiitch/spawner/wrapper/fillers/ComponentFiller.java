package com.github.fabiitch.spawner.wrapper.fillers;

import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.wrapper.EntityWrapper;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public abstract class ComponentFiller<W extends EntityWrapper, C>{

     private ComponentMapper<C> mapper;

     @Setter
     private ComponentListener<C> listener;

     public ComponentFiller(ComponentMapper<C> mapper) {
          this.mapper = mapper;
     }

     public void fill(W entityWrappper, int entityId){
          fill(entityWrappper, mapper.getComponent(entityId));
     }

     public abstract void fill(W entityWrappper, C component);

}
