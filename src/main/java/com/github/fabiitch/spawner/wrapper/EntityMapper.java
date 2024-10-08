package com.github.fabiitch.spawner.wrapper;

import com.badlogic.gdx.utils.IntMap;
import com.github.fabiitch.spawner.factory.Factory;
import com.github.fabiitch.spawner.impact.AspectImpacted;
import com.github.fabiitch.spawner.impact.BehaviorImpacted;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.impact.FlagImpacted;
import com.github.fabiitch.spawner.wrapper.fillers.BehaviorFiller;
import com.github.fabiitch.spawner.wrapper.fillers.ComponentFiller;
import com.github.fabiitch.spawner.wrapper.fillers.FlagFiller;

/**
 * Its use as a cache of your own type
 * You cant query getAll, (use WrapperGroup for this)
 * @param <W>
 */

public class EntityMapper<W extends EntityWrapper> extends BaseEntityMapper<W>
        implements ComponentImpacted, BehaviorImpacted, AspectImpacted, FlagImpacted {

    private final IntMap<W> entities = new IntMap<>();
    private final Factory<W> wrapperFactory;

    public EntityMapper(Factory<W> wrapperFactory) {
        this.wrapperFactory = wrapperFactory;
    }

    public W get(int entityId) {
        W entityWrapper = entities.get(entityId);
        if (entityWrapper == null) {
            return newWrapper(entityId);
        } else {
            return entityWrapper;
        }
    }

    public W reset(int entityId) {
        free(entityId);
        return newWrapper(entityId);
    }

    public void clear() {
        for (W entityWrapper : entities.values()) {
            wrapperFactory.free(entityWrapper);
        }
    }

    public W newWrapper(int entityId) {
        W entityWrapper = wrapperFactory.getNew();
        entityWrapper.setEntityId(entityId);
        for (ComponentFiller<? super W, ?> filler : componentFillers.values()) {
            filler.fill(entityWrapper, entityId);
        }
        for (BehaviorFiller<? super W, ?> filler : behaviorFillers.values()) {
            filler.fill(entityWrapper, entityId);
        }
        for (FlagFiller<? super W> filler : flagFillers.values()) {
            filler.fill(entityWrapper, entityId);
        }
        entities.put(entityId, entityWrapper);
        return entityWrapper;
    }

    public void free(int entityId) {
        W entityWrapper = entities.remove(entityId);
        if (entityWrapper != null)
            wrapperFactory.free(entityWrapper);
    }

    public void updateComponent(int entityId, int indexComponent) {
        W entityWrapper = entities.get(entityId);
        if (entityWrapper != null)
            componentFillers.get(indexComponent).fill(entityWrapper, entityId);
    }

    public void updateBehavior(int entityId, int indexBehavior) {
        W entityWrapper = entities.get(entityId);
        if (entityWrapper != null)
            behaviorFillers.get(indexBehavior).fill(entityWrapper, entityId);
    }

    public void updateFlag(int entityId, int indexFlag) {
        W entityWrapper = entities.get(entityId);
        if (entityWrapper != null)
            flagFillers.get(indexFlag).fill(entityWrapper, entityId);
    }

    @Override
    public boolean impactedByBehavior(int behaviorIndex) {
        return behaviorFillers.containsKey(behaviorIndex);
    }

    @Override
    public boolean impactedByComponent(int indexComponent) {
        return componentFillers.containsKey(indexComponent);
    }

    @Override
    public boolean impactedByFlag(int indexFlag) {
        return flagFillers.containsKey(indexFlag);
    }

    @Override
    public boolean impactedByAspect(int indexAspect) {
        return aspectFillers.containsKey(indexAspect);
    }
}
