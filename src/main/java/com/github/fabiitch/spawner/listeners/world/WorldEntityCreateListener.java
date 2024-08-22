package com.github.fabiitch.spawner.listeners.world;

public interface WorldEntityCreateListener {


    /**
     * Called after engine create a new entityId
     * @param entityId created
     */
    void onEntityCreate(int entityId);
}
