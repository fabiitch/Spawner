package com.github.fabiitch.spawner.signals;

import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.signals.multiplexer.SignalMultiplexer;
import lombok.Getter;

@Getter
public class ComponentSignalMultiplexer<T extends SignalData> extends SignalMultiplexer<T> implements ComponentListener<T> {

    @Override
    public void onComponentAdd(int entityId, T component, int componentIndex) {
        component.setListener(this, entityId);
    }

    @Override
    public void onComponentRemove(int entityId, T component, int componentIndex) {
        component.removeListener();
    }
}
