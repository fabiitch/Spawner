package com.github.fabiitch.spawner.listeners.aspect;

public interface AspectLooseListener<A> {
    void onAspectLoose(int entityId, A component, int indexAspect, int indexComponent);
}
