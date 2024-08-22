package com.github.fabiitch.spawner.operation.locker;

import com.badlogic.gdx.utils.Bits;

public class EcsDataTypeLock {

    private final Bits components = new Bits();
    private final Bits behaviors = new Bits();
    private final Bits aspects = new Bits();
    private final Bits flags = new Bits();



    public boolean isComponentLocked(int indexComponent) {
        return this.components.get(indexComponent);
    }

    public boolean isBehaviorLocked(int indexBehavior) {
        return this.behaviors.get(indexBehavior);
    }

    public boolean isAspectLocked(int indexAspect) {
        return this.aspects.get(indexAspect);
    }

    public boolean isFlagLocked(int indexFlag) {
        return this.flags.get(indexFlag);
    }

    public void lockComponents(Bits components) {
        this.components.or(components);
    }

    public void lockBehaviors(Bits behaviors) {
        this.behaviors.or(behaviors);
    }

    public void lockAspects(Bits aspects) {
        this.aspects.or(aspects);
    }

    public void lockFlags(Bits flags) {
        this.flags.or(flags);
    }

    public void lockComponent(int indexComponent) {
        this.components.set(indexComponent);
    }

    public void lockBehavior(int indexBehavior) {
        this.behaviors.set(indexBehavior);
    }

    public void lockAspect(int indexAspect) {
        this.aspects.set(indexAspect);
    }

    public void lockFlag(int indexFlag) {
        this.flags.set(indexFlag);
    }

}
