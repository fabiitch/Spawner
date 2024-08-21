package com.github.fabiitch.spawner.listeners.behavior;

public interface BehaviorAddComponentListener<B> {
    /**
     * Not called when EntityReference is added in world
     */
    void onBehaviorComponentAdd(int entityId, B behavior, int indexBehavior, int indexComponent);
}
