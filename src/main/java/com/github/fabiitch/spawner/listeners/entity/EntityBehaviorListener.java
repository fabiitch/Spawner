package com.github.fabiitch.spawner.listeners.entity;

public interface EntityBehaviorListener<B> {

    void onBehaviorComponentAdd(int indexBehavior, B componentBehavior, int indexComponent);

    void onBehaviorComponentRemove(int indexBehavior, B componentBehavior, int indexComponent);

    void onBehaviorComponentUpdate(int indexBehavior, B componentBehavior, int indexComponent);

    void onBehaviorGet(int indexBehavior, B componentBehavior, int indexComponent);

    void onBehaviorLoose(int indexBehavior, B componentBehavior, int indexComponent);

}
