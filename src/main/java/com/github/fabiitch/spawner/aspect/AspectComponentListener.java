package com.github.fabiitch.spawner.aspect;

import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.listeners.component.ComponentListener;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class AspectComponentListener<A>implements ComponentListener<A> {
    private final ComponentMapper<A> componentMapper;
    private final AspectMapper<A> aspectMapper;

    @Override
    public void onComponentAdd(int entityId, A component, int componentIndex) {
    }

    @Override
    public void onComponentRemove(int entityId, A component, int componentIndex) {

    }

    @Override
    public void onComponentUpdate(int entityId, A component, int componentIndex) {

    }
}
