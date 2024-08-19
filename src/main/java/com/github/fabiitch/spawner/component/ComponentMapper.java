package com.github.fabiitch.spawner.component;

import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.mappers.ObjectMapper;

public class ComponentMapper<T> extends ObjectMapper<T, ComponentListener<T>> {

    public ComponentMapper(int index) {
        super(index);
    }

    public T getComponent(int entityId) {
        return data.get(entityId);
    }

    public boolean hasComponent(int entityId) {
        return getComponent(entityId) != null;
    }

    public void addComponent(int entityId, T component) {
        data.set(entityId, component);
        notifyAdd(entityId, component);
    }

    public void updated(int entityId) {
        T component = data.get(entityId);
        if (component != null)
            notifyUpdate(entityId, component);
    }

    /**
     * dont call your listeners
     */
    public void addComponentSilent(int entityId, T component) {
        data.set(entityId, component);
    }

    public T removeComponent(int entityId) {
        T component = data.remove(entityId);
        if (component != null)
            notifyRemove(entityId, component);
        return component;
    }

    /**
     * dont call your listeners
     */
    public T removeComponentSilent(int entityId) {
        T component = data.remove(entityId);
        return component;
    }

    private void notifyAdd(int entityId, T component) {
        for (ComponentListener internalListener : internalListeners)
            internalListener.onComponentAdd(entityId, component, index);

        for (ComponentListener<T> listener : listeners)
            listener.onComponentAdd(entityId, component, index);
    }

    private void notifyRemove(int entityId, T component) {
        for (ComponentListener internalListener : internalListeners)
            internalListener.onComponentRemove(entityId, component, index);

        for (ComponentListener<T> listener : listeners)
            listener.onComponentRemove(entityId, component, index);
    }

    private void notifyUpdate(int entityId, T component) {
        for (ComponentListener internalListener : internalListeners)
            internalListener.onComponentUpdate(entityId, component, index);

        for (ComponentListener<T> listener : listeners)
            listener.onComponentUpdate(entityId, component, index);
    }

    void addInternalListener(ComponentListener listener) {
        internalListeners.add(listener);
    }

    void removeInternalListener(ComponentListener listener) {
        internalListeners.removeValue(listener, true);
    }
}
