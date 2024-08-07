package com.github.fabiitch.spawner.entity.mapper.fillers;

import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.entity.EntityWrapper;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public abstract class ComponentFiller<W extends EntityWrapper, C> implements ComponentListener<C>{


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

     @Override
     public void onComponentAdd(int entityId, C component, int componentIndex) {

     }

     @Override
     public void onComponentRemove(int entityId, C component, int componentIndex) {

     }
}
