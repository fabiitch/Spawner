package com.github.fabiitch.spawner.listeners.aspect;

public interface AspectGetListener<A> {
    void onAspectGet(int entityId, A component, int indexAspect, int indexComponent);
}
