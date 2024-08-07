package com.github.fabiitch.spawner.listeners.entity;

public interface EntityBehaviorListener<B> {

    void onBehaviorAdd(int indexBehavior, B componentBehavior);

    void onBehaviorRemove(int indexBehavior, B componentBehavior);

    void onBehaviorGet(int indexBehavior, B componentBehavior);

    void onBehaviorLoose(int indexBehavior, B componentBehavior);

}
