package com.github.fabiitch.spawner.listeners.world;

public interface WorldEntityRemoveListener {

    /**
     * Called before entity was removed (you can access to component)
     *
     * @param entityId removed
     */
    void onEntityRemove(int entityId);
}
