package com.github.fabiitch.spawner.signals;

import com.github.fabiitch.spawner.listeners.BehaviorListener;
import com.github.fabiitch.spawner.signals.multiplexer.SignalMultiplexer;

public class BehaviorSignalMultiplexer<T extends SignalData> extends SignalMultiplexer<T> implements BehaviorListener<T> {

    @Override
    public void onBehaviorComponentAdd(int entityId, T component, int componentIndex) {

    }

    @Override
    public void onBehaviorComponentRemove(int entityId, T component, int componentIndex) {

    }

    @Override
    public void onBehaviorGet(int entityId, T componentBehavior, int behaviorIndex) {
        componentBehavior.setListener(this, entityId);
    }

    @Override
    public void onBehaviorLoose(int entityId, T componentBehavior, int behaviorIndex) {
        componentBehavior.setListener(this, entityId);
    }
}
