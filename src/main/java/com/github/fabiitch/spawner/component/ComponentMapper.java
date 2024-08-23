package com.github.fabiitch.spawner.component;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.groups.EntityData;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.listeners.component.ComponentListener;
import com.github.fabiitch.spawner.utils.mappers.ObjectMapper;

public class ComponentMapper<T> extends ObjectMapper<T, ComponentListener<T>> implements ComponentImpacted {

    ComponentListenerMultiplexer<T> listenerMultiplexer = new ComponentListenerMultiplexer();

    public ComponentMapper(int index) {
        super(index);
    }

    public T getComponent(int entityId) {
        return data.get(entityId);
    }

    public Array<T> getAll(Array<T> components) {
        for (T component : data) {
            if(component != null)
                components.add(component);
        }
        return components;
    }

    public boolean hasComponent(int entityId) {
        return getComponent(entityId) != null;
    }

    public void addComponent(int entityId, T component) {
        data.set(entityId, component);
        notifyAdd(entityId, component);
    }

    /**
     * dont call your listeners
     */
    public void addComponentSilent(int entityId, T component) {
        data.set(entityId, component);
    }

     void addReallyComponent(int entityId, T component) {
        data.set(entityId, component);
        notifyAdd(entityId, component);
    }


    public void updated(int entityId) {
        T component = data.get(entityId);
        if (component != null)
            notifyUpdate(entityId, component);
    }

    public T removeComponent(int entityId) {
        T component = data.remove(entityId);
        if (component != null)
            notifyRemove(entityId, component);
        return component;
    }

    public T removeReally(int entityId) {
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

    @Override
    public boolean impactedByComponent(int indexComponent) {
        return indexComponent == this.index;
    }
}
