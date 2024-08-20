package com.github.fabiitch.spawner.behavior;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.listeners.BehaviorListener;
import com.github.fabiitch.spawner.mappers.ObjectMapper;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.utils.collections.Tab;
import lombok.Getter;
@Getter
public class BehaviorMapper<T> extends ObjectMapper<Tab<T>, BehaviorListener<T>> {

    private final Bits componentsMatch = new Bits();
    private final Array<ComponentMapper<T>> mappers = new Array<>();

    public BehaviorMapper(int index) {
        super(index);
    }

    public SafeTab<T> getBehaviors(int entityId) {
        Tab<T> components = data.get(entityId);
        if(components == null)
            return null;
        return new SafeTab<>(components);//TODO immutable
    }

    public T getBehavior(int entityId, ComponentMapper<T> componentMapper) {
        Tab<T> components = data.get(entityId);
        if (components == null)
            return null;
        return components.get(componentMapper.getIndex());
    }

    public <C> C getComponent(int entityId, ComponentMapper<C> componentMapper) {
        Tab<T> components = data.get(entityId);
        if (components == null)
            return null;
        return componentMapper.getComponent(entityId);
    }

    void addComponent(int entityId, T component, int componentIndex) {
        Tab<T> components = data.get(entityId);
        boolean isGetBehavior = false;
        if (components == null) {
            components = new Tab<>(16);
            data.set(entityId, components);
            isGetBehavior = true;
        }
        components.set(componentIndex, component);

        if (isGetBehavior)
            notifyBehaviorGet(entityId, component, componentIndex);
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
            for (int i = 0; i < components.totalLength(); i++) {
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

    public void updated(int entityId, T behavior){
        notifyBehaviorUpdate(entityId, behavior);
    }

    void clearEntity(int entityId) {
        data.remove(entityId);
    }

    public boolean hasBehavior(int entityId) {
        SafeTab<T> entityBehaviors = getBehaviors(entityId);
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
        for (BehaviorListener internalListener : internalListeners)
            internalListener.onBehaviorComponentAdd(entityId, component, componentIndex);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorComponentAdd(entityId, component, componentIndex);
    }

    private void notifyBehaviorUpdate(int entityId, T component, int componentIndex) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onBehaviorUpdate(entityId, component, componentIndex);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorUpdate(entityId, component, componentIndex);
    }

    private void notifyComponentRemove(int entityId, T component, int componentIndex) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onBehaviorComponentRemove(entityId, component, componentIndex);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorComponentRemove(entityId, component, componentIndex);
    }

    private void notifyBehaviorGet(int entityId, T component, int componentIndex) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onBehaviorGet(entityId, component, componentIndex);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorGet(entityId, component, componentIndex);
    }

    private void notifyBehaviorLoose(int entityId, T component, int componentIndex) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onBehaviorLoose(entityId, component, componentIndex);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorLoose(entityId, component, componentIndex);
    }

    void addInternalListener(BehaviorListener listener) {
        internalListeners.add(listener);
    }

    void removeInternalListener(BehaviorListener listener) {
        internalListeners.removeValue(listener, true);
    }
}
