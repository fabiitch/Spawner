package com.github.fabiitch.spawner.behavior;

import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import lombok.AllArgsConstructor;

/**
 * Listeners added in ComponentMapper
 *
 * @param <T>
 */
@AllArgsConstructor
class BehaviorComponentListener<T> implements ComponentListener<T> {
    private final ComponentMapper<T> componentMapper;
    private final BehaviorMapper<T> behaviorMapper;

    @Override
    public void onComponentAdd(int entityId, T component, int componentIndex) {
        behaviorMapper.addComponent(entityId, component, componentMapper.getIndex());
    }

    @Override
    public void onComponentRemove(int entityId, T component, int componentIndex) {
        behaviorMapper.removeComponent(entityId, componentMapper.getIndex());
    }

    @Override
    public void onComponentUpdate(int entityId, T component, int componentIndex) {
    }
}
