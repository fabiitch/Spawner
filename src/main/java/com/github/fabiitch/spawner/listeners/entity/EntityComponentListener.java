package com.github.fabiitch.spawner.listeners.entity;

public interface EntityComponentListener<C> {

    void onComponentAdd(int componentIndex, C component);

    void onComponentRemove(int componentIndex, C component);
}
