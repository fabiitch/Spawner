package com.github.fabiitch.spawner.listeners.component;

public interface ComponentUpdateListener<C> {

    void onComponentUpdate(int entityId, C component, int componentIndex);
}
