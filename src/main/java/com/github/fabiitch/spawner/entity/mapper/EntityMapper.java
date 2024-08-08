package com.github.fabiitch.spawner.entity.mapper;

import com.badlogic.gdx.utils.IntMap;
import com.github.fabiitch.spawner.archetype.criteria.BehaviorImpacted;
import com.github.fabiitch.spawner.archetype.criteria.ComponentImpacted;
import com.github.fabiitch.spawner.archetype.criteria.FlagImpacted;
import com.github.fabiitch.spawner.entity.EntityWrapper;
import com.github.fabiitch.spawner.entity.mapper.fillers.BehaviorFiller;
import com.github.fabiitch.spawner.entity.mapper.fillers.ComponentFiller;
import com.github.fabiitch.spawner.entity.mapper.fillers.FlagFiller;
import com.github.fabiitch.spawner.factory.Factory;

public class EntityMapper<W extends EntityWrapper> implements ComponentImpacted, FlagImpacted, BehaviorImpacted {

    private final IntMap<ComponentFiller<W, ?>> componentFillers = new IntMap<>();
    private final IntMap<BehaviorFiller<W, ?>> behaviorFillers = new IntMap<>();
    private final IntMap<FlagFiller<W>> flagFillers = new IntMap<>();

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

    public W newWrapper(int entityId) {
        W entityWrapper = wrapperFactory.getNew();
        entityWrapper.setId(entityId);
        for (ComponentFiller<W, ?> filler : componentFillers.values()) {
            filler.fill(entityWrapper, entityId);
        }
        for (BehaviorFiller<W, ?> filler : behaviorFillers.values()) {
            filler.fill(entityWrapper, entityId);
        }
        for (FlagFiller<W> filler : flagFillers.values()) {
            filler.fill(entityWrapper, entityId);
        }
        entities.put(entityId, entityWrapper);
        return entityWrapper;
    }

    public void free(int entityId) {
        W entityWrapper = entities.get(entityId);
        if (entityWrapper != null)
            wrapperFactory.free(entityWrapper);
    }


    public EntityMapper addFiller(ComponentFiller<W, ?> filler) {
        componentFillers.put(filler.getMapper().getIndex(), filler);
        return this;
    }

    public EntityMapper addFiller(BehaviorFiller<W, ?> filler) {
        behaviorFillers.put(filler.getMapper().getIndex(), filler);
        return this;
    }

    public EntityMapper addFiller(FlagFiller<W> filler) {
        flagFillers.put(filler.getMapper().getIndex(), filler);
        return this;
    }

    public EntityMapper remove(ComponentFiller<W, ?> filler) {
        componentFillers.remove(filler.getMapper().getIndex());
        return this;
    }

    public EntityMapper remove(BehaviorFiller<W, ?> filler) {
        behaviorFillers.remove(filler.getMapper().getIndex());
        return this;
    }

    public EntityMapper remove(FlagFiller<W> filler) {
        flagFillers.remove(filler.getMapper().getIndex());
        return this;
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
    public boolean impactedByFlag(int flagIndex) {
        return flagFillers.containsKey(flagIndex);
    }

    protected void clear() {
        for (W entityWrapper : entities.values()) {
            wrapperFactory.free(entityWrapper);
        }
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
}
