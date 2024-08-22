package com.github.fabiitch.spawner.family;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.archetype.builder.Archetype;
import com.github.fabiitch.spawner.entity.EntityManager;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;

public class FamilyManager {
    private final Array<Family> families = new Array<>();
    private final EntityManager entityManager;


    public FamilyManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createFamily(Archetype archetype) {

    }

    public void addFamily(Family family) {
        families.add(family);
        updateFamily(family);
    }

    public void updateFamily(Family family){
        SafeIntArray entities = entityManager.getEntities();
        for(int i = 0 ; i < entities.size() ; i ++){
            family.checkEntity(entities.get(i));
        }
    }

    public void remove(Family family) {
        families.removeValue(family, true);
    }

    public void onEntityAdd(int entityId) {
        for (Family family : families)
            if (family.accept(entityId))
                family.addEntity(entityId);
    }

    public void onEntityRemove(int entityId) {
        for (Family family : families) {
            family.removeEntity(entityId);
        }
    }

    public void onComponentAdd(int entityId, int indexComponent) {
        for (Family family : families)
            if (family.impactedByComponent(indexComponent))
                family.checkEntity(entityId);
    }

    public void onComponentRemove(int entityId, int indexComponent) {
        for (Family family : families)
            if (family.impactedByComponent(indexComponent))
                family.checkEntity(entityId);
    }

    public void onBehaviorGet(int entityId, int indexBehavior) {
        for (Family family : families)
            if(family.impactedByBehavior(indexBehavior))
                family.checkEntity(entityId);
    }

    public void onBehaviorLoose(int entityId, int indexBehavior) {
        for (Family family : families)
            if(family.impactedByBehavior(indexBehavior))
                family.checkEntity(entityId);
    }

    public void onFlagAdd(int entityId, int flagIndex) {
        for (Family family : families)
            if (family.impactedByFlag(flagIndex))
                family.checkEntity(entityId);
    }

    public void onFlagRemove(int entityId, int flagIndex) {
        for (Family family : families)
            if (family.impactedByFlag(flagIndex))
                family.checkEntity(entityId);
    }
}
