package com.github.fabiitch.spawner.behavior;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntIntMap;
import com.github.fabiitch.spawner.archetype.criteria.BehaviorImpacted;
import com.github.fabiitch.spawner.archetype.criteria.ComponentImpacted;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.listeners.BehaviorListener;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.utils.collections.Tab;
import com.github.fabiitch.spawner.utils.mappers.ObjectMapper;
import lombok.Getter;

@Getter
public class BehaviorMapper<T> extends ObjectMapper<Tab<T>, BehaviorListener<T>> implements BehaviorImpacted , ComponentImpacted {

    private final Array<ComponentMapper<T>> mappers = new Array<>();
    private final IntIntMap componentRelativeIndex = new IntIntMap();

    private int incrementRelative = 0;

    public BehaviorMapper(int index) {
        super(index);
    }

    public SafeTab<T> getBehaviors(int entityId) {
        Tab<T> components = data.get(entityId);
        if (components == null)
            return null;
        return new SafeTab<>(components);//TODO immutable
    }

    public T getBehavior(int entityId, int componentIndex) {
        Tab<T> components = data.get(entityId);
        if (components == null)
            return null;
        return components.get(componentIndex);
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
                    notifyBehaviorLoose(entityId, component, componentIndex);
                }
                notifyComponentRemove(entityId, component, componentIndex);
            }
        }
    }

    public Tab<T> removeBehavior(int entityId) {
        Tab<T> components = data.remove(entityId);
        if (components != null) {
            T lastRemoved = null;
            int componentIndexRemove = -1;
            for (int i = 0; i < components.totalLength(); i++) {
                T component = components.get(i);
                if (component != null) {
                    lastRemoved = component;
                    componentIndexRemove = i;
                    mappers.get(i).removeComponent(entityId);
                }
            }
            if (lastRemoved != null)
                notifyBehaviorLoose(entityId, lastRemoved, componentIndexRemove);
        }
        return components;
    }

    public void updated(int entityId, T behavior) {
        Tab<T> tab = data.get(entityId);
        if (tab != null) {
            int index = tab.indexOf(behavior);
            if (index != -1) {
                notifyBehaviorUpdate(entityId, behavior, index);
            }
        }
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
        componentRelativeIndex.
    }

    private void notifyComponentAdd(int entityId, T component, int componentIndex) {
        for (BehaviorListener internalListener : internalListeners)
            internalListener.onBehaviorComponentAdd(entityId, component, index, componentIndex);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorComponentAdd(entityId, component, index, componentIndex);
    }

    private void notifyBehaviorUpdate(int entityId, T component, int componentIndex) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onBehaviorUpdate(entityId, component, index, componentIndex);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorUpdate(entityId, component, index, componentIndex);
    }

    private void notifyComponentRemove(int entityId, T component, int componentIndex) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onBehaviorComponentRemove(entityId, component, index, componentIndex);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorComponentRemove(entityId, component, index, componentIndex);
    }

    private void notifyBehaviorGet(int entityId, T component, int componentIndex) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onBehaviorGet(entityId, component, index, componentIndex);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorGet(entityId, component, index, componentIndex);
    }

    private void notifyBehaviorLoose(int entityId, T component, int componentIndex) {
        for (BehaviorListener<T> internalListener : internalListeners)
            internalListener.onBehaviorLoose(entityId, component, index, componentIndex);

        for (BehaviorListener<T> listener : listeners)
            listener.onBehaviorLoose(entityId, component, index, componentIndex);
    }

    void addInternalListener(BehaviorListener listener) {
        internalListeners.add(listener);
    }

    void removeInternalListener(BehaviorListener listener) {
        internalListeners.removeValue(listener, true);
    }

    @Override
    public boolean impactedByBehavior(int behaviorIndex) {
        return behaviorIndex == this.index;

    }

    @Override
    public boolean impactedByComponent(int indexComponent) {
        return false;
    }
}
