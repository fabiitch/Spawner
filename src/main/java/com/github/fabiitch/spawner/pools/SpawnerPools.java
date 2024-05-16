package com.github.fabiitch.spawner.pools;

import com.github.fabiitch.spawner.entity.EntityReference;

public interface SpawnerPools {

     EntityReference getEntityReference();

     void free(EntityReference entityReference);


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
