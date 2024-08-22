package com.github.fabiitch.spawner.archetype.builder;

import com.badlogic.gdx.utils.Bits;
import com.github.fabiitch.spawner.archetype.IArchetype;
import com.github.fabiitch.spawner.entity.EntityManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Archetype implements IArchetype {

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
        return true;
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
        return true;
    }

    @Override
    public boolean impactedByAspect(int indexAspect) {
        if (behaviorFilterData != null)
            return behaviorFilterData.impacted(indexAspect);
        return true;
    }

    @Override
    public boolean aspectMatch(Bits aspectBits) {
        if (aspectFilterData != null)
            return aspectFilterData.accept(aspectBits);
        return true;
    }

    @Override
    public boolean impactedByFlag(int indexFlag) {
        if (flagFilterData != null)
            return flagFilterData.impacted(indexFlag);
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
                && aspectMatch(entityManager.getAspectBits(entityId))
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
