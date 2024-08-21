package com.github.fabiitch.spawner.listeners.groups;

public interface ComponentGroupListener<C> {

    void onAdd(int entityId, C component);

    void onRemove(int entityId, C component);
}
