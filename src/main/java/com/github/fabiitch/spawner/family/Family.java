package com.github.fabiitch.spawner.family;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.Archetype;
import com.github.fabiitch.spawner.archetype.criteria.*;
import com.github.fabiitch.spawner.impact.BehaviorImpacted;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.impact.FlagImpacted;
import com.github.fabiitch.spawner.listeners.FamilyListener;
import com.github.fabiitch.spawner.query.EntityFilter;
import com.github.fabiitch.spawner.utils.sort.EntityComparator;
import com.github.fabiitch.spawner.utils.collections.IntList;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;

public class Family implements EntityFilter,
        ComponentsMatcher, ComponentImpacted,
        BehaviorsMatcher, BehaviorImpacted,
        FlagsMatcher, FlagImpacted {

    private final Archetype archetype;
    private final Array<FamilyListener> listeners = new Array<>();

    private final IntArray entities = new IntArray();
    private SafeIntArray safeIntArray = new SafeIntArray(entities);


    public Family(Archetype archetype) {
        this.archetype = archetype;
    }

    public SafeIntArray getEntities() {
        return safeIntArray;
    }

    public IntArray getEntities(IntArray result) {
        result.addAll(entities);
        return result;
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
    public boolean impactedByFlag(int indexFlag) {
        return archetype.impactedByFlag(indexFlag);
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
        this.safeIntArray = new SafeIntArray(entities);
    }

    public void sortByEntityId() {
        entities.sort();
    }
}
