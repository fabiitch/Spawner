package com.github.fabiitch.spawner.behavior;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntIntMap;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.impact.BehaviorImpacted;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.listeners.behavior.BehaviorListener;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.utils.collections.Tab;
import com.github.fabiitch.spawner.utils.mappers.ObjectMapper;

public class BehaviorMapper<B> extends ObjectMapper<Tab<B>, BehaviorListener<B>> implements BehaviorImpacted , ComponentImpacted {

    private final Array<ComponentMapper<B>> mappers = new Array<>();
    private final IntIntMap componentRelativeIndex = new IntIntMap();

    private Bits componentsMatch = new Bits();


    public BehaviorMapper(int index) {
        super(index);
    }

    public SafeTab<B> getBehaviors(int entityId) {
        Tab<B> components = data.get(entityId);
        if (components == null)
            return null;
        return new SafeTab<>(components);//TODO immutable
    }

    public B getBehavior(int entityId, int componentIndex) {
        Tab<B> components = data.get(entityId);
        if (components == null)
            return null;
        return components.get(componentIndex);
    }

    public <C extends B> C getComponent(int entityId, ComponentMapper<C> componentMapper) {
        Tab<B> components = data.get(entityId);
        if (components == null)
            return null;
        return componentMapper.getComponent(entityId);
    }

    void addComponent(int entityId, B component, int componentIndex) {
        Tab<B> components = data.get(entityId);
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
        Tab<B> components = data.get(entityId);
        if (components != null) {
            B component = components.remove(componentIndex);
            if (component != null) {
                if (components.isEmpty()) {
                    notifyBehaviorLoose(entityId, component, componentIndex);
                }
                notifyComponentRemove(entityId, component, componentIndex);
            }
        }
    }

    public Tab<B> removeBehavior(int entityId) {
        Tab<B> components = data.remove(entityId);
        if (components != null) {
            B lastRemoved = null;
            int componentIndexRemove = -1;
            for (int i = 0; i < components.totalLength(); i++) {
                B component = components.get(i);
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

    public void updated(int entityId, B behavior) {
        Tab<B> tab = data.get(entityId);
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
        SafeTab<B> entityBehaviors = getBehaviors(entityId);
        return entityBehaviors != null && !entityBehaviors.isEmpty();
    }

    public boolean matchComponent(int componentIndex) {
        return componentsMatch.get(componentIndex);
    }


    void registerComponentMapper(ComponentMapper<B> componentMapper) {
        mappers.add(componentMapper);
        componentsMatch.set(componentMapper.getIndex());
    }

    private void notifyComponentAdd(int entityId, B component, int componentIndex) {
        for (BehaviorListener internalListener : internalListeners)
            internalListener.onBehaviorComponentAdd(entityId, component, index, componentIndex);

        for (BehaviorListener<B> listener : listeners)
            listener.onBehaviorComponentAdd(entityId, component, index, componentIndex);
    }

    private void notifyBehaviorUpdate(int entityId, B component, int componentIndex) {
        for (BehaviorListener<B> internalListener : internalListeners)
            internalListener.onBehaviorUpdate(entityId, component, index, componentIndex);

        for (BehaviorListener<B> listener : listeners)
            listener.onBehaviorUpdate(entityId, component, index, componentIndex);
    }

    private void notifyComponentRemove(int entityId, B component, int componentIndex) {
        for (BehaviorListener<B> internalListener : internalListeners)
            internalListener.onBehaviorComponentRemove(entityId, component, index, componentIndex);

        for (BehaviorListener<B> listener : listeners)
            listener.onBehaviorComponentRemove(entityId, component, index, componentIndex);
    }

    private void notifyBehaviorGet(int entityId, B component, int componentIndex) {
        for (BehaviorListener<B> internalListener : internalListeners)
            internalListener.onBehaviorGet(entityId, component, index, componentIndex);

        for (BehaviorListener<B> listener : listeners)
            listener.onBehaviorGet(entityId, component, index, componentIndex);
    }

    private void notifyBehaviorLoose(int entityId, B component, int componentIndex) {
        for (BehaviorListener<B> internalListener : internalListeners)
            internalListener.onBehaviorLoose(entityId, component, index, componentIndex);

        for (BehaviorListener<B> listener : listeners)
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
    public boolean impactedByComponent(int indexComponent) { //TODO
        return false;
    }
}
