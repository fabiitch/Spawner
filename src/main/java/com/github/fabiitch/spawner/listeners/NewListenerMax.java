package com.github.fabiitch.spawner.listeners;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.listeners.aspect.AspectListener;
import com.github.fabiitch.spawner.listeners.behavior.BehaviorListener;
import com.github.fabiitch.spawner.listeners.component.ComponentListener;
import com.github.fabiitch.spawner.listeners.component.ComponentListenerMultiplexer;
import com.github.fabiitch.spawner.listeners.flag.FlagListener;

public class NewListenerMax implements ComponentListener, BehaviorListener, AspectListener, FlagListener {

    private Array<ComponentListenerMultiplexer> componentInternalListeners = new Array<>();
    private Array<ComponentListenerMultiplexer> componentlisteners = new Array<>();



    @Override
    public void onComponentAdd(int entityId, Object component, int componentIndex) {
        componentInternalListeners.get(componentIndex).onComponentAdd(entityId, component, componentIndex);
        componentlisteners.get(componentIndex).onComponentAdd(entityId, component, componentIndex);

    }

    @Override
    public void onComponentRemove(int entityId, Object component, int componentIndex) {
        componentInternalListeners.get(componentIndex).onComponentRemove(entityId, component, componentIndex);
        componentlisteners.get(componentIndex).onComponentRemove(entityId, component, componentIndex);
    }

    @Override
    public void onComponentUpdate(int entityId, Object component, int componentIndex) {
        componentInternalListeners.get(componentIndex).onComponentUpdate(entityId, component, componentIndex);
        componentlisteners.get(componentIndex).onComponentUpdate(entityId, component, componentIndex);
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

    @Override
    public void onBehaviorComponentAdd(int entityId, Object behavior, int indexBehavior, int indexComponent) {

    }

    @Override
    public void onBehaviorGet(int entityId, Object behavior, int indexBehavior, int indexComponent) {

    }

    @Override
    public void onBehaviorLoose(int entityId, Object behavior, int indexBehavior, int indexComponent) {

    }

    @Override
    public void onBehaviorComponentRemove(int entityId, Object behavior, int indexBehavior, int indexComponent) {

    }

    @Override
    public void onBehaviorUpdate(int entityId, Object behavior, int indexBehavior, int indexComponent) {

    }

    @Override
    public void onFlagAdd(int entityId, int flagIndex) {

    }

    @Override
    public void onFlagRemove(int entityId, int flagIndex) {

    }
}
