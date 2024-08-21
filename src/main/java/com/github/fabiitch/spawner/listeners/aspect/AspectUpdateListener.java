package com.github.fabiitch.spawner.listeners.aspect;

public interface AspectUpdateListener<A> {
    void onAspectUpdated(int entityId, A component, int indexAspect, int indexComponent);
}
