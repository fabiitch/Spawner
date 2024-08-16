package com.github.fabiitch.spawner.family;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.Archetype;
import com.github.fabiitch.spawner.archetype.criteria.*;
import com.github.fabiitch.spawner.listeners.FamilyListener;
import com.github.fabiitch.spawner.sort.EntityComparator;
import com.github.fabiitch.spawner.utils.collections.IntList;
import lombok.Getter;

public class Family implements EntityMatcher,
        ComponentsMatcher, ComponentImpacted,
        BehaviorsMatcher, BehaviorImpacted,
        FlagsMatcher, FlagImpacted {

    private final Archetype archetype;
    private final Array<FamilyListener> listeners = new Array<>();

    @Getter
    private final IntArray entities = new IntArray();


    public Family(Archetype archetype) {
        this.archetype = archetype;
    }

    @Override
    public boolean accept(int entityId) {
        return archetype.accept(entityId);
    }

    public void checkEntity(int entityId) {
        boolean present = hasEntity(entityId);
        boolean accept = accept(entityId);
        if (accept && !present)
            addEntity(entityId);
        else if (!accept && present)
            removeEntity(entityId);
    }

    public boolean hasEntity(int entityId) {
        return entities.contains(entityId);
    }

    public boolean hasEntities(int... entityIds) {
        for (int entityId : entityIds) {
            if (!hasEntity(entityId))
                return false;
        }
        return true;
    }

    public void addEntity(int entityId) {
        entities.add(entityId);
        for (FamilyListener listener : listeners) {
            listener.onEntityAdd(entityId);
        }
    }

    public void removeEntity(int entityId) {
        if (hasEntity(entityId)) {
            entities.removeValue(entityId);
            for (FamilyListener listener : listeners) {
                listener.onEntityRemove(entityId);
            }
        }
    }

    public void addListener(FamilyListener listener) {
        listeners.add(listener);
    }

    public void removeListener(FamilyListener listener) {
        listeners.removeValue(listener, true);
    }

    @Override
    public boolean componentsMatch(Bits componentBits) {
        return archetype.componentsMatch(componentBits);
    }

    @Override
    public boolean impactedByComponent(int indexComponent) {
        return archetype.impactedByComponent(indexComponent);
    }

    @Override
    public boolean impactedByBehavior(int behaviorIndex) {
        return archetype.impactedByBehavior(behaviorIndex);
    }

    @Override
    public boolean behaviorsMatch(Bits behaviorBits) {
        return archetype.behaviorsMatch(behaviorBits);
    }

    @Override
    public boolean impactedByFlag(int flagIndex) {
        return archetype.impactedByFlag(flagIndex);
    }

    @Override
    public boolean flagsMatch(Bits flagBits) {
        return archetype.flagsMatch(flagBits);
    }

    public void sort(EntityComparator comparator) {
        final IntList internalList4Sort = new IntList();
        internalList4Sort.setValues(entities.toArray());
        internalList4Sort.sort(comparator);
        System.arraycopy(internalList4Sort.getValues(), 0, entities.items, 0, entities.size);
    }

    public void sortByEntityId() {
        entities.sort();
    }
}
