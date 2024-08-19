package com.github.fabiitch.spawner.signals;

public abstract class SignalData {

    private int entityId;
    private SignalListener<SignalData> listener;

    public void updated() {
        listener.onUpdate(entityId, this);
    }

    void setListener(SignalListener listener, int entityId) {
        this.listener = listener;
        this.entityId = entityId;
    }

    void removeListener() {
        this.listener = null;
        this.entityId = -1;
    }
}
