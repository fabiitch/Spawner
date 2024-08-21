package com.github.fabiitch.spawner.aspect;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.github.fabiitch.spawner.listeners.ListenerManager;

public class AspectManager {
    private final ListenerManager listenerManager;
    private int increment4Aspect = -1;

    private final ObjectIntMap<Class<?>> aspectIndexMap = new ObjectIntMap<>();
    private final Array<AspectMapper<?>> mappers = new Array<>(); //component index

    public AspectManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }

    public AspectMapper getMapper(Class<?> aspectClass) {
        int behaviorIndex = aspectIndexMap.get(aspectClass, -1);
        return mappers.get(behaviorIndex);
    }

    public AspectMapper getMapper(int mapperIndex) {
        return mappers.get(mapperIndex);
    }
    public boolean existMapper(Class<?> behaviorClass) {
        int mapperIndex = aspectIndexMap.get(behaviorClass, -1);
        return mapperIndex != -1;
    }
    public AspectMapper registerAspect(Class<?> aspectClass) {
        increment4Aspect++;
        AspectMapper<?> mapper = new AspectMapper<>(increment4Aspect);

        mappers.add(mapper);
        mapper.addInternalListener(listenerManager);

        aspectIndexMap.put(aspectClass, increment4Aspect);
        return mapper;
    }
}
