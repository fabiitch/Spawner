package com.github.fabiitch.spawner.listeners.component;

public interface ComponentAddListener<C> {

    /**
     * Not called when EntityReference is added in world
     */
    void onComponentAdd(int entityId, C component, int componentIndex);
}
