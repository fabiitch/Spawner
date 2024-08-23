package com.github.fabiitch.spawner.listeners.component;

import com.badlogic.gdx.utils.Array;

public class ComponentListenerMultiplexer<C> implements ComponentListener<C> {

    private final Array<ComponentAddListener<C>> addListeners = new Array<>();
    private final Array<ComponentRemoveListener<C>> removeListeners = new Array<>();
    private final Array<ComponentUpdateListener<C>> updateListeners = new Array<>();

    public void addListener(ComponentAddListener<C> componentAddListener) {
        addListeners.add(componentAddListener);
    }

    public void addListener(ComponentRemoveListener<C> componentRemoveListener) {
        removeListeners.add(componentRemoveListener);
    }

    public void addListener(ComponentUpdateListener<C> componentUpdateListener) {
        updateListeners.add(componentUpdateListener);
    }

    public void addListener(ComponentListener<C> componentListener) {
        addListeners.add(componentListener);
        removeListeners.add(componentListener);
        updateListeners.add(componentListener);
    }

    @Override
    public void onComponentAdd(int entityId, C component, int componentIndex) {
        for (ComponentAddListener<C> addListener : addListeners) {
            addListener.onComponentAdd(entityId, component, componentIndex);
        }
    }

    @Override
    public void onComponentRemove(int entityId, C component, int componentIndex) {
        for (ComponentRemoveListener<C> removeListener : removeListeners) {
            removeListener.onComponentRemove(entityId, component, componentIndex);
        }
    }

    @Override
    public void onComponentUpdate(int entityId, C component, int componentIndex) {
        for (ComponentUpdateListener<C> updateListener : updateListeners) {
            updateListener.onComponentUpdate(entityId, component, componentIndex);
        }
    }
}
