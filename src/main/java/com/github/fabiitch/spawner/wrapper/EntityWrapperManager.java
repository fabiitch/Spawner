package com.github.fabiitch.spawner.wrapper;

import com.badlogic.gdx.utils.Array;

public class EntityWrapperManager {

    private final Array<EntityMapper<?>> mappers = new Array<>();

    public void addMapper(EntityMapper<?> mapper) {
        mappers.add(mapper);
    }

    public void removeMapper(EntityMapper<?> mapper) {
        mappers.removeValue(mapper,true);
    }

    public void onComponentAdd(int entityId, int indexComponent) {
        for (EntityMapper<?> entityMapper : mappers)
            if (entityMapper.impactedByComponent(indexComponent))
                entityMapper.updateComponent(entityId,  indexComponent);
    }

    public void onComponentRemove(int entityId, int indexComponent) {
        for (EntityMapper<?> entityMapper : mappers)
            if (entityMapper.impactedByComponent(indexComponent))
                entityMapper.updateComponent(entityId,  indexComponent);
    }

    public void onBehaviorGet(int entityId, int indexBehavior) {
        for (EntityMapper<?> entityMapper : mappers)
            if (entityMapper.impactedByBehavior(indexBehavior))
                entityMapper.updateBehavior(entityId,  indexBehavior);
    }

    public void onBehaviorLoose(int entityId, int indexBehavior) {
        for (EntityMapper<?> entityMapper : mappers)
            if (entityMapper.impactedByBehavior(indexBehavior))
                entityMapper.updateBehavior(entityId,  indexBehavior);
    }

    public void onFlagAdd(int entityId, int flagIndex) {
        for (EntityMapper<?> entityMapper : mappers)
            if (entityMapper.impactedByFlag(flagIndex))
                entityMapper.updateFlag(entityId,  flagIndex);
    }

    public void onFlagRemove(int entityId, int flagIndex) {
        for (EntityMapper<?> entityMapper : mappers)
            if (entityMapper.impactedByFlag(flagIndex))
                entityMapper.updateFlag(entityId,  flagIndex);
    }
}
