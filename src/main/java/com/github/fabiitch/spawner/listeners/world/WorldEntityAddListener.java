package com.github.fabiitch.spawner.listeners.world;

public interface WorldEntityAddListener {

    /**
     * Called after entity was reconstructed (you can access to component)
     *
     * @param entityId added
     */
    void onEntityAdd(int entityId);
}
