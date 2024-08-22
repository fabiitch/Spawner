package com.github.fabiitch.spawner.listeners.world;

public interface WorldEntityDestroyListener {

    /**
     * called before entity was destroyed (you can access to component)
     *
     * @param entityId destroyed
     */
    void onEntityDestroy(int entityId);
}
