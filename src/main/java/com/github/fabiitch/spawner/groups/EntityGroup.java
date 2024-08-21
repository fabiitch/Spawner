package com.github.fabiitch.spawner.groups;

import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.criteria.EntityFilterTracker;
import com.github.fabiitch.spawner.listeners.BehaviorListener;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.listeners.FlagListener;
import com.github.fabiitch.spawner.listeners.WorldListener;
import com.github.fabiitch.spawner.listeners.entity.EntityListener;

public class EntityGroup implements EntityFilterTracker, WorldListener, ComponentListener, BehaviorListener, FlagListener {

    private Bits entities = new Bits();
    private EntityFilterTracker filterTracker;

    public void checkEntity(int entityId) {
        boolean has = entities.get(entityId);
        boolean accept = filterTracker.accept(entityId);
        if (!has && accept)
            entities.set(entityId);
        else if (has && !accept)
            entities.clear(entityId);
    }

    @Override
    public boolean accept(int entityId) {
        return filterTracker.accept(entityId);
    }

    @Override
    public boolean impactedByBehavior(int behaviorIndex) {
        return filterTracker.impactedByBehavior(behaviorIndex);
    }

    @Override
    public boolean impactedByComponent(int indexComponent) {
        return filterTracker.impactedByComponent(indexComponent);
    }

    @Override
    public boolean impactedByFlag(int flagIndex) {
        return filterTracker.impactedByFlag(flagIndex);
    }

    @Override
    public void onBehaviorComponentAdd(int entityId, Object component, int indexBehavior, int componentIndex) {
        if(filterTracker.impactedByBehavior(indexBehavior))
            checkEntity(entityId);
    }

    @Override
    public void onBehaviorComponentRemove(int entityId, Object component, int indexBehavior, int componentIndex) {

    }

    @Override
    public void onBehaviorUpdate(int entityId, Object component, int indexBehavior, int componentIndex) {

    }

    @Override
    public void onBehaviorGet(int entityId, Object componentBehavior, int indexBehavior, int componentIndex) {

    }

    @Override
    public void onBehaviorLoose(int entityId, Object componentBehavior, int indexBehavior, int componentIndex) {

    }

    @Override
    public void onComponentAdd(int entityId, Object component, int componentIndex) {

    }

    @Override
    public void onComponentRemove(int entityId, Object component, int componentIndex) {

    }

    @Override
    public void onComponentUpdate(int entityId, Object component, int componentIndex) {

    }

    @Override
    public void onFlagAdd(int entityId, int flagIndex) {

    }

    @Override
    public void onFlagRemove(int entityId, int flagIndex) {

    }

    @Override
    public void onEntityCreate(int entityId) {

    }

    @Override
    public void onEntityAdd(int entityId) {

    }

    @Override
    public void onEntityRemove(int entityId) {

    }

    @Override
    public void onEntityDestroy(int entityId) {

    }
}
