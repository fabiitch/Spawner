package com.github.fabiitch.spawner.listeners.groups;

public interface BehaviorGroupListener<B> {

    void onAdd(int entityId, B behavior, int componentIndex);
    void onRemove(int entityId, B behavior, int componentIndex);
}
