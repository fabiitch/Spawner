package com.github.fabiitch.spawner.entity;

import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.Pool;
import com.github.fabiitch.spawner.flag.FlagMapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Prototype is just a way to create an entity with all components
 * You must create a new prototype for each entity
 */
@Getter
public class Prototype implements Pool.Poolable {

    private final Map<Class<?>, Object> componentsMap = new HashMap<>();
    private final Bits flagBits = new Bits();

    public Prototype addComponent(Object object, Class<?> targetClass) {
        componentsMap.put(targetClass, object);
        return this;
    }

    public Prototype addComponent(Object object) {
        componentsMap.put(object.getClass(), object);
        return this;
    }

    public Prototype removeComponent(Class<?> targetClass) {
        componentsMap.remove(targetClass);
        return this;
    }

    public Prototype removeComponent(Object object) {
        for (Map.Entry<Class<?>, Object> componentEntry : componentsMap.entrySet()) {
            if (componentEntry.getValue() == object) {
                removeComponent(componentEntry.getKey());
                break;
            }
        }
        return this;
    }

    public Prototype addFlag(int flagIndex) {
        flagBits.set(flagIndex);
        return this;
    }

    public Prototype addFlag(FlagMapper flagMapper) {
        flagBits.set(flagMapper.getIndex());
        return this;
    }

    public Prototype removeFlag(int flagIndex) {
        flagBits.clear(flagIndex);
        return this;
    }

    public Prototype removeFlag(FlagMapper flagMapper) {
        flagBits.clear(flagMapper.getIndex());
        return this;
    }

    @Override
    public void reset() {
        componentsMap.clear();
        flagBits.clear();
    }


}
