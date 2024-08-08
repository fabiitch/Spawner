package com.github.fabiitch.spawner.component.signal;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.github.fabiitch.spawner.behavior.BehaviorManager;
import com.github.fabiitch.spawner.component.ComponentManager;
import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.listeners.ListenerManager;

public class ComponentSignalManager extends ComponentManager {

    Array<SignalListener<?>> listeners = new Array<>();

    public ComponentSignalManager(ListenerManager listenerManager, BehaviorManager behaviorManager) {
        super(listenerManager, behaviorManager);
    }

    @Override
    public void addEntity(EntityReference entityReference) {
        super.addEntity(entityReference);
    }

    @Override
    public void removeEntity(EntityReference entityReference) {
        super.removeEntity(entityReference);
    }

    @Override
    public void destroyEntity(int entityId, Bits componentsBits) {
        super.destroyEntity(entityId, componentsBits);
    }
}
