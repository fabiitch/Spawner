package com.github.fabiitch.spawner.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;
import com.github.fabiitch.spawner.behavior.BehaviorMapper;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.flag.FlagMapper;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import lombok.Getter;

/**
 * Permit to get an entity with all component attach
 * Use it if you want to remove an entity and re add it after
 */
public class EntityReference implements EntityWrapper, Pool.Poolable {
    private int id = -1;
    @Getter
    private final IntMap<Object> components = new IntMap<>();

    @Getter
    private final Bits componentBits = new Bits();
    @Getter
    private final Bits flagBits = new Bits();
    @Getter
    private final Bits behaviorBits = new Bits();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void addComponent(int componentIndex, Object component) {
        components.put(componentIndex, component);
    }

    public void removeComponent(int componentIndex) {
        components.remove(componentIndex);
    }


    public void setComponentBits(Bits bits) {
        this.componentBits.or(bits);
    }

    public void setFlagBits(Bits bits) {
        this.flagBits.or(bits);
    }

    public void setBehaviorBits(Bits behaviorBits) {
        this.behaviorBits.or(behaviorBits);
    }

    public <C> C getComponent(ComponentMapper<C> mapper) {
        return (C) components.get(mapper.getIndex());
    }

    public boolean hasComponent(ComponentMapper<?> mapper) {
        return componentBits.get(mapper.getIndex());
    }

    public boolean hasFlag(FlagMapper mapper) {
        return flagBits.get(mapper.getIndex());
    }

    public <B> Array<B> getBehavior(BehaviorMapper<B> behaviorMapper) {
        Array<B> array = new Array<>();
        for (ComponentMapper<B> componentMapper : behaviorMapper.getMappers()) {
            if (hasComponent(componentMapper))
                array.add(getComponent(componentMapper));
        }
        return array;
    }

    @Override
    public void reset() {
        id = -1;
        components.clear();
        componentBits.clear();
        behaviorBits.clear();
        flagBits.clear();
    }


}
