package com.github.fabiitch.spawner.signals;

public interface SignalListener<C extends SignalData> {

    void onUpdate(int entityId, C signalData);
}
