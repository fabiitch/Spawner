package com.github.fabiitch.spawner.archetype.builder;

import com.badlogic.gdx.utils.Bits;
import com.github.fabiitch.spawner.archetype.IArchetype;
import com.github.fabiitch.spawner.query.matchers.conditions.BaseMatcher;

public class ArchetypeMulti extends BaseMatcher implements IArchetype {

    public ArchetypeMulti() {
    }

    @Override
    public boolean aspectMatch(Bits aspectBits) {
        return false;
    }

    @Override
    public boolean behaviorsMatch(Bits behaviorBits) {
        return false;
    }

    @Override
    public boolean componentsMatch(Bits componentsBits) {
        return false;
    }

    @Override
    public boolean flagsMatch(Bits flagBits) {
        return false;
    }

    @Override
    public boolean impactedByAspect(int indexAspect) {
        return false;
    }

    @Override
    public boolean impactedByBehavior(int behaviorIndex) {
        return false;
    }

    @Override
    public boolean impactedByComponent(int indexComponent) {
        return false;
    }

    @Override
    public boolean impactedByFlag(int indexFlag) {
        return false;
    }

    @Override
    public boolean accept(int entityId) {
        return false;
    }
}
