package com.github.fabiitch.spawner.listeners.behavior;

public interface BehaviorRemoveComponentListener<B> {
    /**
     * Not called when EntityReference is removed or destroy
     */
    void onBehaviorComponentRemove(int entityId, B behavior, int indexBehavior, int indexComponent);
}
