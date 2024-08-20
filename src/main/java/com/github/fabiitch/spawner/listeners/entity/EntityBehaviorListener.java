package com.github.fabiitch.spawner.listeners.entity;

public interface EntityBehaviorListener<B> {

    void onBehaviorComponentAdd(int indexBehavior, B componentBehavior, int componentIndex);

    void onBehaviorComponentRemove(int indexBehavior, B componentBehavior, int componentIndex);

    void onBehaviorComponentUpdate(int indexBehavior, B componentBehavior, int componentIndex);

    void onBehaviorGet(int indexBehavior, B componentBehavior, int componentIndex);

    void onBehaviorLoose(int indexBehavior, B componentBehavior, int componentIndex);

}
