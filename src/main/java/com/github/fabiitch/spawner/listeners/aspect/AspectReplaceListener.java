package com.github.fabiitch.spawner.listeners.aspect;

public interface AspectReplaceListener<A> {

    void onAspectReplace(int entityId, A oldComponent, A newComponent, int indexAspect, int indexOldComponent, int indexOldBehavior);

}
