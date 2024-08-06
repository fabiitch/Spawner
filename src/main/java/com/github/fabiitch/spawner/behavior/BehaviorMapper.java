package com.github.fabiitch.spawner.behavior;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.listeners.BehaviorListener;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.mappers.ObjectMapper;
import com.github.fabiitch.spawner.utils.collections.Tab;
import lombok.Getter;
@Getter
public class BehaviorMapper<T> extends ObjectMapper<Tab<T>, BehaviorListener<T>> {

    private final Bits componentsMatch = new Bits();
    private final Array<ComponentMapper<T>> mappers = new Array<>();

    public BehaviorMapper(int index) {
        super(index);
    }

    public Tab<T> getBehaviors(int entityId) {
        Tab<T> components = data.get(entityId);
        return components; //TODO immutable
    }

    void addComponent(int entityId, T component, int componentIndex) {
        Tab<T> components = data.get(entityId);
        if (components == null) {
            components = new Tab<>(16);
            data.set(entityId, components);
            notifyBehaviorGet(entityId, component);
        }
        components.set(componentIndex, component);

        notifyComponentAdd(entityId, component, componentIndex);
    }

    void removeComponent(int entityId, int componentIndex) {
        Tab<T> components = data.get(entityId);
        if (components != null) {
            T component = components.remove(componentIndex);
            if (component != null) {
                if (components.isEmpty()) {
                    notifyBehaviorLoose(entityId, component);
                }
                notifyComponentRemove(entityId, component, componentIndex);
            }
        }
    }

    public void removeBehavior(int entityId) {
        Tab<T> components = data.remove(entityId);
        if (components != null) {
            T lastRemoved = null;
            for (int i = 0; i < components.totalLenght(); i++) {
                T component = components.get(i);
                if (component != null) {
                    lastRemoved = component;
                    mappers.get(i).removeComponent(entityId);
                    notifyComponentRemove(entityId, component, i);
                }
            }
            if (lastRemoved != null)
                notifyBehaviorLoose(entityId, lastRemoved);
        }
    }

    void clearEntity(int entityId) {
        data.remove(entityId);
    }

    public boolean hasBehavior(int entityId) {
        Tab<T> entityBehaviors = getBehaviors(entityId);
        return entityBehaviors != null && !entityBehaviors.isEmpty();
    }

    public boolean matchComponent(int componentIndex) {
        return componentsMatch.get(componentIndex);
    }


    void registerComponentMapper(ComponentMapper<T> componentMapper) {
        mappers.add(componentMapper);
        componentsMatch.set(componentMapper.getIndex());
    }


    private void notifyComponentAdd(int entityId, T component, int componentIndex) {
        for (ComponentListener internalListener : internalListeners)
            internalListener.onComponentAdd(entityId, component, componentIndex);

        for (ComponentListener<T> listener : listeners)
            listener.onComponentAdd(entityId, component, componentIndex);
    }

    private void notifyComponentRemove(int entityId, T component, int componentIndex) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onComponentRemove(entityId, component, componentIndex);

        for (ComponentListener<T> listener : listeners)
            listener.onComponentRemove(entityId, component, componentIndex);
    }

    private void notifyBehaviorGet(int entityId, T component) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onBehaviorGet(entityId, component, index);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorGet(entityId, component, index);
    }

    private void notifyBehaviorLoose(int entityId, T component) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onBehaviorLoose(entityId, component, index);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorLoose(entityId, component, index);
    }

    void addInternalListener(BehaviorListener listener) {
        internalListeners.add(listener);
    }

    void removeInternalListener(BehaviorListener listener) {
        internalListeners.removeValue(listener, true);
    }
}
