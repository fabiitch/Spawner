package com.github.fabiitch.spawner.listeners.behavior;

public interface BehaviorUpdateListener<B> {
    void onBehaviorUpdate(int entityId, B behavior, int indexBehavior, int indexComponent);
}
