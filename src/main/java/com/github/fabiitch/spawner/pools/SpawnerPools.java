package com.github.fabiitch.spawner.pools;

import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.groups.components.EntityData;

public interface SpawnerPools {

     EntityReference getEntityReference();

     void free(EntityReference entityReference);


    <T> EntityData<T> getEntityComponent();

    void free(EntityData entityData);
//
//    public <T> Tab<EntityComponent<T>> getEntityComponent(Tab<T> tab){
//        EntityComponent obtain = Pools.obtain(EntityComponent.class);
//        int max = tab.notNullSize;
//        int size = tab.totalLenght();
//
//        for(int i = 0 ; i < size ; i ++){
//
//        }
//    }
}
