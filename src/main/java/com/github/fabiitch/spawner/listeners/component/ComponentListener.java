package com.github.fabiitch.spawner.listeners.component;


public interface ComponentListener<C> extends
        ComponentAddListener<C>,
        ComponentRemoveListener<C>,
        ComponentUpdateListener<C> {

}
