package com.github.fabiitch.spawner.listeners;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.listeners.component.ComponentListenerMultiplexer;

public class EngineListenerMultiplexer <M>{

    private Array<M> internalMultiplexer = new Array<>();
    private Array<M> publicMultiplexer = new Array<>();


    public M getInternal(int index){
        return internalMultiplexer.get(index);
    }
    public M getPublic(int index){
        return publicMultiplexer.get(index);
    }

}
