package com.github.fabiitch.spawner.listeners;


public interface ComponentListener<T> {

    /**
     * Not called when EntityReference is added in world
     * @param entityId
     * @param component
     */
    void onComponentAdd(int entityId, T component, int componentIndex);

    /**
     * Not called when EntityReference is removed or destroy
     * @param entityId
     * @param component
     */
    void onComponentRemove(int entityId, T component, int componentIndex);
}
