package com.github.fabiitch.spawner.listeners.behavior;

public interface BehaviorGetListener<B> {

    /**
     * Called when entity get the behavior (not for each component with behavior)
     * Not called when EntityReference is added in world
     */
    void onBehaviorGet(int entityId, B behavior, int indexBehavior, int indexComponent);
}
