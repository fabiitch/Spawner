package com.github.fabiitch.spawner.groups;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.criteria.BehaviorImpacted;
import com.github.fabiitch.spawner.archetype.criteria.BehaviorsMatcher;
import com.github.fabiitch.spawner.archetype.criteria.ComponentImpacted;
import com.github.fabiitch.spawner.behavior.BehaviorMapper;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.listeners.BehaviorListener;
import com.github.fabiitch.spawner.query.BehaviorFilter;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.utils.collections.Tab;

public class BehaviorGroup<B> implements BehaviorListener<B>, BehaviorImpacted, ComponentImpacted, BehaviorsMatcher {

    private BehaviorMapper<B> mapper;
    private BehaviorFilter<B> matcher;

    private final Tab<IntArray> data = new Tab<>(); //entityId,ComponentIndex

    public BehaviorGroup(BehaviorMapper<B> mapper, BehaviorFilter<B> matcher) {
        this.mapper = mapper;
        this.matcher = matcher;
        mapper.addListener(this);
        init();
    }

    public void remove() {
        mapper.removeListener(this);
        data.clear();
    }

    private void add(int entityId, int componentIndex) {
        IntArray array = data.get(entityId);
        if (array == null) {
            array = new IntArray(mapper.getMappers().size);
            data.set(entityId, array);
        }
        array.add(componentIndex);

    }

    private void remove(int entityId, int componentIndex) {
        IntArray array = data.get(entityId);
        if (array != null) {
            array.removeValue(componentIndex);
        }
    }

    public SafeTab<SafeIntArray> getAll() {
        return null;
    }

    public void init() {
        SafeTab<Tab<B>> all = mapper.getAll();
        for (int entityId = 0; entityId < all.size(); entityId++) {
            for (int indexComponent = 0; indexComponent <)
                if (matcher.accept(entityId)) {
                }
        }
    }

    public boolean has(int entityId, int componentIndex) {
        return data.get(entityId, componentIndex) != null;
    }

    public Array<B> get(int entityId, int array) {
        IntArray entityArray = entities.get(entityId);
        if (entityArray != null) {
            for (int i = 0; i < array.size; i++) {
                int componentIndex = entityArray.get(i);
                array.add(mapper.getBehavior(entityId, componentIndex));
            }
        }
        return array;
    }

    public <C> C getComponent(int entityId, ComponentMapper<C> componentMapper) {
        if (has(entityId, componentMapper.getIndex())) {
            return componentMapper.getComponent(entityId);
        }
        return null;
    }


    @Override
    public void onBehaviorComponentAdd(int entityId, B component, int behaviorIndex, int componentIndex) {
        if (matcher.accept(entityId, component)) {
            add(entityId, componentIndex);
        }
    }

    @Override
    public void onBehaviorComponentRemove(int entityId, B component, int behaviorIndex, int componentIndex) {
        remove(entityId, componentIndex);
    }

    @Override
    public void onBehaviorUpdate(int entityId, B component, int behaviorIndex, int componentIndex) {
        boolean accept = matcher.accept(entityId, component);
        boolean present = has(entityId, componentIndex);
        if (accept && !present) {
            add(entityId, componentIndex);
        } else if (!accept && present) {
            remove(entityId, componentIndex);
        }
    }

    @Override
    public void onBehaviorGet(int entityId, B componentBehavior, int behaviorIndex, int componentIndex) {

    }

    @Override
    public void onBehaviorLoose(int entityId, B componentBehavior, int behaviorIndex, int componentIndex) {

    }

    @Override
    public boolean impactedByBehavior(int behaviorIndex) {
        return mapper.impactedByBehavior(behaviorIndex);
    }

    @Override
    public boolean impactedByComponent(int indexComponent) {
        return mapper.impactedByComponent(indexComponent);
    }
}
