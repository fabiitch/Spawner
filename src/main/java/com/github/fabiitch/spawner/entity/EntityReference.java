package com.github.fabiitch.spawner.entity;

import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;
import lombok.Getter;

public class EntityReference implements EntityWrapper, Pool.Poolable {
    private int id = -1;
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

    public IntMap<Object> getComponents() {
        return components;
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

    @Override
    public void reset() {
        components.clear();
        componentBits.clear();
        behaviorBits.clear();
        flagBits.clear();
    }


}
