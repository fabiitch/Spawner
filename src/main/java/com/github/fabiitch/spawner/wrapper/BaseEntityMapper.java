package com.github.fabiitch.spawner.wrapper;

import com.badlogic.gdx.utils.IntMap;
import com.github.fabiitch.spawner.impact.AspectImpacted;
import com.github.fabiitch.spawner.impact.BehaviorImpacted;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.impact.FlagImpacted;
import com.github.fabiitch.spawner.wrapper.fillers.AspectFiller;
import com.github.fabiitch.spawner.wrapper.fillers.BehaviorFiller;
import com.github.fabiitch.spawner.wrapper.fillers.ComponentFiller;
import com.github.fabiitch.spawner.wrapper.fillers.FlagFiller;

public class BaseEntityMapper<W extends EntityWrapper>
        implements ComponentImpacted, BehaviorImpacted, AspectImpacted, FlagImpacted {

    protected final IntMap<ComponentFiller<? super W, ?>> componentFillers = new IntMap<>();
    protected final IntMap<BehaviorFiller<? super W, ?>> behaviorFillers = new IntMap<>();
    protected final IntMap<AspectFiller<? super W, ?>> aspectFillers = new IntMap<>();
    protected final IntMap<FlagFiller<? super W>> flagFillers = new IntMap<>();

    public void useFillerOf(BaseEntityMapper<? super W> entityMapper) {
        componentFillers.putAll(entityMapper.componentFillers);
        behaviorFillers.putAll(entityMapper.behaviorFillers);
        flagFillers.putAll(entityMapper.flagFillers);
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
    public boolean impactedByAspect(int indexAspect) {
        return aspectFillers.containsKey(indexAspect);
    }

    @Override
    public boolean impactedByFlag(int indexFlag) {
        return flagFillers.containsKey(indexFlag);
    }


    public BaseEntityMapper addFiller(ComponentFiller<W, ?> filler) {
        componentFillers.put(filler.getMapper().getIndex(), filler);
        return this;
    }

    public BaseEntityMapper addFiller(BehaviorFiller<W, ?> filler) {
        behaviorFillers.put(filler.getMapper().getIndex(), filler);
        return this;
    }

    public BaseEntityMapper addFiller(FlagFiller<W> filler) {
        flagFillers.put(filler.getMapper().getIndex(), filler);
        return this;
    }

    public BaseEntityMapper removeFiller(ComponentFiller<W, ?> filler) {
        componentFillers.remove(filler.getMapper().getIndex());
        return this;
    }

    public BaseEntityMapper removeFiller(BehaviorFiller<W, ?> filler) {
        behaviorFillers.remove(filler.getMapper().getIndex());
        return this;
    }

    public BaseEntityMapper removeFiller(FlagFiller<W> filler) {
        flagFillers.remove(filler.getMapper().getIndex());
        return this;
    }


}
