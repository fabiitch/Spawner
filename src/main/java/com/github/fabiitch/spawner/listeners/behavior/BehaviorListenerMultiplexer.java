package com.github.fabiitch.spawner.listeners.behavior;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.listeners.component.ComponentAddListener;
import com.github.fabiitch.spawner.listeners.component.ComponentRemoveListener;
import com.github.fabiitch.spawner.listeners.component.ComponentUpdateListener;

public class BehaviorListenerMultiplexer<B> implements BehaviorListener<B> {

    private final Array<ComponentAddListener<B>> addListeners = new Array<>();
    private final Array<ComponentRemoveListener<B>> removeListeners = new Array<>();
    private final Array<ComponentUpdateListener<B>> updateListeners = new Array<>();
    private final Array<ComponentAddListener<B>> looseListeners = new Array<>();
    private final Array<ComponentRemoveListener<B>> getListeners = new Array<>();

    @Override
    public void onBehaviorComponentAdd(int entityId, B behavior, int indexBehavior, int indexComponent) {
        for (ComponentAddListener<B> addListener : addListeners) {
            addListener.onComponentAdd(entityId, behavior, indexBehavior);
        }
    }

    @Override
    public void onBehaviorGet(int entityId, B behavior, int indexBehavior, int indexComponent) {
        for (ComponentRemoveListener<B> getListener : getListeners) {
            getListener.onComponentRemove(entityId, behavior, indexBehavior);
        }
    }

    @Override
    public void onBehaviorLoose(int entityId, B behavior, int indexBehavior, int indexComponent) {
        for (ComponentAddListener<B> looseListener : looseListeners) {
            looseListener.onComponentAdd(entityId, behavior, indexBehavior);
        }

    }

    @Override
    public void onBehaviorComponentRemove(int entityId, B behavior, int indexBehavior, int indexComponent) {
        for (ComponentRemoveListener<B> removeListener : removeListeners) {
            removeListener.onComponentRemove(entityId, behavior, indexBehavior);
        }
    }

    @Override
    public void onBehaviorUpdate(int entityId, B behavior, int indexBehavior, int indexComponent) {
        for (ComponentUpdateListener<B> updateListener : updateListeners) {
            updateListener.onComponentUpdate(entityId, behavior, indexBehavior);
        }
    }
}
