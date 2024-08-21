package com.github.fabiitch.spawner.utils;

import com.badlogic.gdx.utils.IntIntMap;
import com.github.fabiitch.spawner.component.ComponentMapper;

public class RelativeComponentIndex {

    private IntIntMap map = new IntIntMap();
    private int incrementIndex;

    public int size() {
        return incrementIndex;
    }

    public void addComponent(ComponentMapper mapper) {
        map.put(mapper.getIndex(), incrementIndex);
        incrementIndex++;
    }

    public int getRelative(ComponentMapper mapper) {
        return map.get(mapper.getIndex(), -1);
    }

    public int getRelative(int mapperIndex) {
        return map.get(mapperIndex, -1);
    }
}
