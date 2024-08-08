package com.github.fabiitch.spawner.component.signal;

public interface SignalListener<T extends SignalComponent> {

    void onUpdate(T signalComponent);
}
