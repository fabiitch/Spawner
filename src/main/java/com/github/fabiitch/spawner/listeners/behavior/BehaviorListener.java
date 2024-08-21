package com.github.fabiitch.spawner.listeners.behavior;


public interface BehaviorListener<B> extends
        BehaviorAddComponentListener<B>,
        BehaviorRemoveComponentListener<B>,
        BehaviorGetListener<B>,
        BehaviorLooseListener<B>,
        BehaviorUpdateListener<B> {


}
