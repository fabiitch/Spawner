package com.github.fabiitch.spawner.groups;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.behavior.BehaviorMapper;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.listeners.BehaviorListener;
import com.github.fabiitch.spawner.query.BehaviorMatcher;
import com.github.fabiitch.spawner.utils.collections.Tab;

public class BehaviorGroup<B> implements BehaviorListener<B> {

    private BehaviorMapper<B> mapper;
    private BehaviorMatcher<B> matcher;

    private Tab<IntArray> result = new Tab<>();

    public boolean has(int entityId, int componentIndex) {
        IntArray array = result.get(entityId);
        if (array == null) return false;
        return array.contains(componentIndex);
    }

    public <C> C getComponent(int entityId, ComponentMapper<C> componentMapper) {
        if (has(entityId, componentMapper.getIndex())) {
            return componentMapper.getComponent(entityId);
        }
        return null;
    }

    private void add(int entityId, B component, int componentIndex) {
        IntArray array = result.get(entityId);
        if (array == null) {
            array = new IntArray();
            result.set(entityId, array);
        }
        array.add(componentIndex);
    }

    private void remove(int entityId, int componentIndex) {
        IntArray array = result.get(entityId);
        if (array != null && array.size > componentIndex) {
            array.removeIndex(componentIndex);
        }
    }

    @Override
    public void onBehaviorComponentAdd(int entityId, B component, int componentIndex) {
        if (matcher.accept(entityId, component)) {
            add(entityId, component, componentIndex);
        }
    }

    @Override
    public void onBehaviorComponentRemove(int entityId, B component, int componentIndex) {
        remove(entityId, componentIndex);
    }

    @Override
    public void onBehaviorUpdate(int entityId, B component, int componentIndex) {
        boolean accept = matcher.accept(entityId, component);
        boolean present = has(entityId, componentIndex);
        if (accept && !present) {
            add(entityId, component, componentIndex);
        } else if (!accept && present) {
            remove(entityId, componentIndex);
        }
    }

    @Override
    public void onBehaviorGet(int entityId, B componentBehavior, int behaviorIndex) {

    }

    @Override
    public void onBehaviorLoose(int entityId, B componentBehavior, int behaviorIndex) {

    }
}
