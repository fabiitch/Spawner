package com.github.fabiitch.spawner.archetype;

import com.badlogic.gdx.utils.Bits;
import com.github.fabiitch.spawner.archetype.criteria.AspectsMatcher;
import com.github.fabiitch.spawner.archetype.criteria.BehaviorsMatcher;
import com.github.fabiitch.spawner.archetype.criteria.ComponentsMatcher;
import com.github.fabiitch.spawner.archetype.criteria.FlagsMatcher;
import com.github.fabiitch.spawner.entity.EntityManager;
import com.github.fabiitch.spawner.impact.AspectImpacted;
import com.github.fabiitch.spawner.impact.BehaviorImpacted;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.impact.FlagImpacted;
import com.github.fabiitch.spawner.query.EntityFilter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Archetype implements EntityFilter,
        ComponentImpacted, ComponentsMatcher,
        BehaviorImpacted, BehaviorsMatcher,
        AspectImpacted, AspectsMatcher,
        FlagImpacted, FlagsMatcher {

    private final EntityManager entityManager;

    private final BitsCriteria componentFilterData;
    private final BitsCriteria behaviorFilterData;
    private final BitsCriteria aspectFilterData;
    private final BitsCriteria flagFilterData;

    @Override
    public boolean impactedByComponent(int indexComponent) {
        if (componentFilterData != null)
            return componentFilterData.impacted(indexComponent);
        return false;
    }

    @Override
    public boolean componentsMatch(Bits componentsBits) {
        if (componentFilterData != null)
            return componentFilterData.accept(componentsBits);
        return false;
    }

    @Override
    public boolean impactedByBehavior(int indexBehavior) {
        if (behaviorFilterData != null)
            return behaviorFilterData.impacted(indexBehavior);
        return false;
    }

    @Override
    public boolean behaviorsMatch(Bits behaviorBits) {
        if (behaviorFilterData != null)
            return behaviorFilterData.accept(behaviorBits);
        return false;
    }

    @Override
    public boolean impactedByAspect(int indexAspect) {
        if (behaviorFilterData != null)
            return behaviorFilterData.impacted(indexAspect);
        return false;
    }

    @Override
    public boolean aspectmatch(Bits aspectBits) {
        if (aspectFilterData != null)
            return aspectFilterData.accept(aspectBits);
        return false;
    }

    @Override
    public boolean impactedByFlag(int indexFlag) {
        if (flagFilterData != null)
            return flagFilterData.impacted(indexFlag);
        return false;
    }

    @Override
    public boolean flagsMatch(Bits flagBits) {
        if (flagFilterData != null)
            return flagFilterData.accept(flagBits);
        return false;
    }

    @Override
    public boolean accept(int entityId) {
        return flagsMatch(entityManager.getFlagBits(entityId))
                && behaviorsMatch(entityManager.getBehaviorBits(entityId))
                && aspectmatch(entityManager.getAspectBits(entityId))
                && componentsMatch(entityManager.getComponentBits(entityId));
    }

    public boolean equals(Archetype archetype) {
        boolean equal = true;
        if (componentFilterData != null)
            equal = componentFilterData.equals(archetype.componentFilterData);
        if (equal && behaviorFilterData != null)
            equal = behaviorFilterData.equals(archetype.behaviorFilterData);
        if (equal && flagFilterData != null)
            equal = flagFilterData.equals(archetype.flagFilterData);
        if (equal && flagFilterData != null)
            equal = flagFilterData.equals(archetype.flagFilterData);
        return equal;
    }
}

