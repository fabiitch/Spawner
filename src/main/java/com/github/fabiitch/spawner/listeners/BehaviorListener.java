package com.github.fabiitch.spawner.listeners;


public interface BehaviorListener<T> extends ComponentListener<T> {

    /**
     * Called when entity get the behavior (not for each component with behavior)
     * Not called when EntityReference is added in world
     * @param entityId
     * @param componentBehavior
     * @param behaviorIndex
     */
    void onBehaviorGet(int entityId, T componentBehavior, int behaviorIndex);

    /**
     * Called when entity loose the last component with the behavior
     * Not called when EntityReference is removed or destroy
     * @param entityId
     * @param componentBehavior
     * @param behaviorIndex
     */
    void onBehaviorLoose(int entityId, T componentBehavior, int behaviorIndex);


}
