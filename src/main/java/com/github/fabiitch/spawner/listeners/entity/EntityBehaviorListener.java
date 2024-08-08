package com.github.fabiitch.spawner.listeners.entity;

public interface EntityBehaviorListener<B> {

    void onBehaviorComponentAdd(int indexBehavior, B componentBehavior);

    void onBehaviorComponentRemove(int indexBehavior, B componentBehavior);

    void onBehaviorGet(int indexBehavior, B componentBehavior);

    void onBehaviorLoose(int indexBehavior, B componentBehavior);

}
