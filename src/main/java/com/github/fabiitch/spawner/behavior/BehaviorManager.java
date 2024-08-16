package com.github.fabiitch.spawner.behavior;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.github.fabiitch.spawner.component.ComponentManager;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.listeners.BehaviorListener;
import com.github.fabiitch.spawner.listeners.ListenerManager;

public class BehaviorManager {
    private final ListenerManager listenerManager;
    private int increment4Behavior = -1;


    private final IntIntMap componentBehaviorMap = new IntIntMap();

    private final ObjectIntMap<Class<?>> behaviorIndexMap = new ObjectIntMap<>();
    private final Array<BehaviorMapper<?>> mappers = new Array<>(); //component index


    public BehaviorManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }

    public BehaviorMapper getMapper(Class<?> behaviorClass) {
        int behaviorIndex = behaviorIndexMap.get(behaviorClass, -1);
        return mappers.get(behaviorIndex);
    }

    public BehaviorMapper getMapper(int mapperIndex) {
        return mappers.get(mapperIndex);
    }

    public boolean existMapper(Class<?> behaviorClass) {
        int mapperIndex = behaviorIndexMap.get(behaviorClass, -1);
        return mapperIndex != -1;
    }

    public BehaviorMapper registerBehavior(Class<?> behaviorClass) {
        increment4Behavior++;
        BehaviorMapper<?> mapper = new BehaviorMapper<>(increment4Behavior);
        mappers.add(mapper);
        mapper.addInternalListener(listenerManager);

        behaviorIndexMap.put(behaviorClass, increment4Behavior);
        return mapper;
    }

    public <T> void onRegisterComponent(Class<T> componentClass, ComponentMapper<T> componentMapper, ComponentManager componentManager) {
        int componentIndex = componentMapper.getIndex();

        for (ObjectIntMap.Entry<Class<?>> behaviorMapperEntry : behaviorIndexMap.entries()) {
            Class<?> behaviorClass = behaviorMapperEntry.key;
            int mapperIndex = behaviorMapperEntry.value;

            if (ClassReflection.isAssignableFrom(behaviorClass, componentClass)) {
                BehaviorMapper<T> behaviorMapper = getMapper(mapperIndex);
                componentBehaviorMap.put(componentIndex, behaviorMapper.getIndex());
                behaviorMapper.registerComponentMapper(componentMapper);
                componentManager.addInternalListener(componentMapper.getIndex(), new BehaviorComponentListener(behaviorMapper));
            }
        }
    }

    public void addInternalListener(int indexBehavior, BehaviorListener listener) {
        BehaviorMapper mapper = getMapper(indexBehavior);
        mapper.addInternalListener(listener);
    }

    public void removeInternalListener(int indexBehavior, BehaviorListener listener) {
        BehaviorMapper mapper = getMapper(indexBehavior);
        mapper.removeInternalListener(listener);
    }

    public void destroyEntity(int entityId, Bits behaviorBits) {
        int nextSetBit = behaviorBits.nextSetBit(0);
        while (nextSetBit != -1) {
            BehaviorMapper<?> mapper = mappers.get(nextSetBit);
            mapper.clearEntity(entityId);
            nextSetBit = behaviorBits.nextSetBit(++nextSetBit);
        }
    }

}
