package com.github.fabiitch.spawner.signals.multiplexer;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.signals.SignalData;
import com.github.fabiitch.spawner.signals.SignalListener;

public class SignalMultiplexer<T extends SignalData> implements SignalListener<T> {
    protected final Array<SignalListener<T>> array = new Array<>();

    public void addListener(SignalListener<T> listener) {
        array.add(listener);
    }

    public void removeListener(SignalListener<T> listener) {
        array.removeValue(listener, true);
    }

    @Override
    public void onUpdate(int entityId, T signalData) {
        for (SignalListener<T> listener : array) {
            listener.onUpdate(entityId, signalData);
        }
    }
}
