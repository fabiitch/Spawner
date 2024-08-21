package com.github.fabiitch.spawner.listeners.behavior;

public interface BehaviorLooseListener<B> {

    /**
     * Called when entity loose the last component with the behavior
     * Not called when EntityReference is removed or destroy
     */
    void onBehaviorLoose(int entityId, B behavior, int indexBehavior, int indexComponent);
}
