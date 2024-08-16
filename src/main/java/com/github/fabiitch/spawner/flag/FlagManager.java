package com.github.fabiitch.spawner.flag;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.listeners.ListenerManager;

public class FlagManager {
    private final ListenerManager listenerManager;
    private int increment4Flags = -1;
    private final ObjectIntMap<String> flagIndexMap = new ObjectIntMap<>();
    private final Array<FlagMapper> mappers = new Array<>(); //component index

    public FlagManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }

    public FlagMapper getMapper(Class<?> flagClass) {
        int flagIndex = flagIndexMap.get(flagClass.getSimpleName(), -1);
        return mappers.get(flagIndex);
    }

    public FlagMapper getMapper(String flagName) {
        int flagIndex = flagIndexMap.get(flagName, -1);
        return mappers.get(flagIndex);
    }


    public FlagMapper getMapper(int flagIndex) {
        return mappers.get(flagIndex);
    }

    public boolean existMapper(Class<?> flagClass) {
        int mapperIndex = flagIndexMap.get(flagClass.getSimpleName(), -1);
        return mapperIndex != -1;
    }

    public boolean existMapper(String flagName) {
        int mapperIndex = flagIndexMap.get(flagName, -1);
        return mapperIndex != -1;
    }

    public FlagMapper registerFlag(Class<?> flagClass) {
        return registerFlag(flagClass.getSimpleName());
    }

    public FlagMapper registerFlag(String flagName) {
        int present = flagIndexMap.get(flagName, -1);
        if (present >= 0)
            return mappers.get(present);

        increment4Flags++;

        FlagMapper mapper = new FlagMapper(increment4Flags);
        mapper.addInternalListener(listenerManager);
        flagIndexMap.put(flagName, increment4Flags);
        mappers.add(mapper);

        return mapper;
    }

    public void destroyEntity(int entityId, Bits flagBits) {
        int nextSetBit = flagBits.nextSetBit(0);
        while (nextSetBit != -1) {
            FlagMapper mapper = mappers.get(nextSetBit);
            mapper.removeUnsafe(entityId);
            nextSetBit = flagBits.nextSetBit(++nextSetBit);
        }
    }

    public void addEntity(EntityReference entityReference) {
        int entityId = entityReference.getEntityId();
        Bits flagBits = entityReference.getFlagBits();

        int nextSetBit = flagBits.nextSetBit(0);
        while (nextSetBit != -1) {
            FlagMapper mapper = mappers.get(nextSetBit);
            mapper.setUnsafe(entityId);
            nextSetBit = flagBits.nextSetBit(++nextSetBit);
        }
    }
}
