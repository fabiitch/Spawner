package com.github.fabiitch.spawner.groups;

import com.badlogic.gdx.utils.Bits;
import com.github.fabiitch.spawner.World;
import com.github.fabiitch.spawner.archetype.criteria.EntityFilterTracker;
import com.github.fabiitch.spawner.listeners.aspect.AspectListener;
import com.github.fabiitch.spawner.listeners.behavior.BehaviorListener;
import com.github.fabiitch.spawner.listeners.component.ComponentListener;
import com.github.fabiitch.spawner.listeners.flag.FlagListener;
import com.github.fabiitch.spawner.listeners.world.WorldListener;

public class EntityGroup implements EntityFilterTracker,
        WorldListener, ComponentListener, BehaviorListener, AspectListener, FlagListener {

    private Bits entities = new Bits();
    private EntityFilterTracker filterTracker;


    public EntityGroup(World world) {
    }

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
    public boolean impactedByAspect(int indexAspect) {
        return filterTracker.impactedByAspect(indexAspect);
    }

    @Override
    public boolean impactedByFlag(int indexFlag) {
        return filterTracker.impactedByFlag(indexFlag);
    }


    @Override
    public void onBehaviorComponentAdd(int entityId, Object component, int indexBehavior, int componentIndex) {
        if (filterTracker.impactedByBehavior(indexBehavior))
            checkEntity(entityId);
    }

    @Override
    public void onBehaviorComponentRemove(int entityId, Object component, int indexBehavior, int indexComponent) {

    }

    @Override
    public void onBehaviorUpdate(int entityId, Object component, int indexBehavior, int indexComponent) {

    }

    @Override
    public void onBehaviorGet(int entityId, Object componentBehavior, int indexBehavior, int indexComponent) {

    }

    @Override
    public void onBehaviorLoose(int entityId, Object componentBehavior, int indexBehavior, int indexComponent) {

    }

    @Override
    public void onComponentAdd(int entityId, Object component, int indexComponent) {

    }

    @Override
    public void onComponentRemove(int entityId, Object component, int indexComponent) {

    }

    @Override
    public void onComponentUpdate(int entityId, Object component, int indexComponent) {

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

    @Override
    public void onAspectGet(int entityId, Object component, int indexAspect, int indexComponent) {

    }

    @Override
    public void onAspectLoose(int entityId, Object component, int indexAspect, int indexComponent) {

    }

    @Override
    public void onAspectReplace(int entityId, Object oldComponent, Object newComponent, int indexAspect, int indexOldComponent, int indexOldBehavior) {

    }

    @Override
    public void onAspectUpdated(int entityId, Object component, int indexAspect, int indexComponent) {

    }
}
