package com.github.fabiitch.spawner.component;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.github.fabiitch.spawner.behavior.BehaviorManager;
import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.listeners.ListenerManager;

public class ComponentManager {

    private final ListenerManager listenerManager;
    private final BehaviorManager behaviorManager;

    private final ObjectIntMap<Class<?>> componentIndexMap = new ObjectIntMap<>();
    private final Array<ComponentMapper<?>> mappers = new Array<>(); //component index
    private int increment4Component = -1;


    public ComponentManager(ListenerManager listenerManager, BehaviorManager behaviorManager) {
        this.listenerManager = listenerManager;
        this.behaviorManager = behaviorManager;
    }

    public ComponentMapper getMapper(Class<?> componentClass) {
        int componentIndex = componentIndexMap.get(componentClass, -1);
        return mappers.get(componentIndex);
    }

    public ComponentMapper getMapper(int componentIndex) {
        return mappers.get(componentIndex);
    }

    public boolean existMapper(Class<?> componentClass) {
        int mapperIndex = componentIndexMap.get(componentClass, -1);
        return mapperIndex != -1;
    }

    public <T> ComponentMapper<T> registerComponent(Class<T> componentClass) {
        int present = componentIndexMap.get(componentClass, -1);
        if (present >= 0)
            return (ComponentMapper<T>) mappers.get(present);

        increment4Component++;
        ComponentMapper<T> mapper = new ComponentMapper(increment4Component);
        componentIndexMap.put(componentClass, increment4Component);

        mappers.add(mapper);
        mapper.addInternalListener(listenerManager);

        behaviorManager.onRegisterComponent(componentClass, mapper, this);

        return mapper;
    }

    public void destroyEntity(int entityId, Bits componentsBits) {
        int nextSetBit = componentsBits.nextSetBit(0);
        while (nextSetBit != -1) {
            ComponentMapper<?> mapper = mappers.get(nextSetBit);
            mapper.removeComponentSilent(entityId);
            nextSetBit = componentsBits.nextSetBit(++nextSetBit);
        }
    }

    public void removeEntity(EntityReference entityReference) {
        int entityId = entityReference.getId();
        Bits componentsBits = entityReference.getComponentBits();

        int nextSetBit = componentsBits.nextSetBit(0);
        while (nextSetBit != -1) {
            ComponentMapper<?> mapper = mappers.get(nextSetBit);
            entityReference.addComponent(nextSetBit, mapper.getComponent(entityId));
            mapper.removeComponentSilent(entityId);
            nextSetBit = componentsBits.nextSetBit(++nextSetBit);
        }
    }

    public void addEntity(EntityReference entityReference){
        int entityId = entityReference.getId();
        for (IntMap.Entry<Object> componentEnty : entityReference.getComponents()) {
            ComponentMapper mapper = getMapper(componentEnty.key);
            mapper.addComponentSilent(entityId, componentEnty.value);
        }
    }

    public void addInternalListener(int componentIndex, ComponentListener listener) {
        mappers.get(componentIndex).addInternalListener(listener);
    }

    public void removeInternalListener(int componentIndex, ComponentListener listener) {
        mappers.get(componentIndex).removeInternalListener(listener);
    }
}
