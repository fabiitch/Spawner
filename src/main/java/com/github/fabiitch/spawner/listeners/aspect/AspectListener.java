package com.github.fabiitch.spawner.listeners.aspect;

public interface AspectListener<A> extends
        AspectGetListener<A>,
        AspectLooseListener<A>,
        AspectReplaceListener<A>,
        AspectUpdateListener<A> {

}
