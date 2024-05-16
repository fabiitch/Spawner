package com.github.fabiitch.spawner.listeners;

public interface WorldListener {

    /**
     * Called after engine create a new entityId
     * @param entityId created
     */
    void onEntityCreate(int entityId);

    /**
     * Called after entity was reconstructed (you can access to component)
     *
     * @param entityId added
     */
    void onEntityAdd(int entityId);

    /**
     * Called before entity was removed (you can access to component)
     *
     * @param entityId removed
     */
    void onEntityRemove(int entityId);


    /**
     * called before entity was destroyed (you can access to component)
     * @param entityId destroyed
     */
    void onEntityDestroy(int entityId);
}
