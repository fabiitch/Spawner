package com.github.fabiitch.spawner.archetype;

import com.badlogic.gdx.utils.Bits;
import com.github.fabiitch.spawner.archetype.criteria.*;
import com.github.fabiitch.spawner.entity.EntityManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Archetype implements EntityMatcher,
        ComponentImpacted,
        ComponentsMatcher,
        BehaviorImpacted,
        BehaviorMatcher,
        FlagImpacted,
        FlagMatcher {

    private final EntityManager entityManager;

    private final BitsCriteria componentFilterData;
    private final BitsCriteria behaviorFilterData;
    private final BitsCriteria flagFilterData;

    @Override
    public boolean impactedByComponent(int indexComponent) {
        if (componentFilterData != null)
            return componentFilterData.impacted(indexComponent);
        return true;
    }

    @Override
    public boolean componentsMatch(Bits componentsBits) {
        if (componentFilterData != null)
            return componentFilterData.accept(componentsBits);
        return true;
    }

    @Override
    public boolean impactedByBehavior(int behaviorIndex) {
        if (behaviorFilterData != null)
            return behaviorFilterData.impacted(behaviorIndex);
        return true;
    }

    @Override
    public boolean behaviorsMatch(Bits behaviorBits) {
        if (behaviorFilterData != null)
            return behaviorFilterData.accept(behaviorBits);
        return true;
    }


    @Override
    public boolean impactedByFlag(int flagIndex) {
        if (flagFilterData != null)
            return flagFilterData.impacted(flagIndex);
        return true;
    }


    @Override
    public boolean flagsMatch(Bits flagBits) {
        if (flagFilterData != null)
            return flagFilterData.accept(flagBits);
        return true;
    }

    @Override
    public boolean accept(int entityId) {
        return flagsMatch(entityManager.getFlagBits(entityId))
                && behaviorsMatch(entityManager.getBehaviorBits(entityId))
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
        return equal;
    }


}

