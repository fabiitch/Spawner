package com.github.fabiitch.spawner.query.group;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.query.group.filters.ComponentFilter;
import com.github.fabiitch.spawner.utils.collections.Tab;
import lombok.Getter;

@Getter
public class ComponentGroup<T> extends Tab<T> implements ComponentFilter<T> {

    private Array<ComponentFilter<T>> filters = new Array<>();

    @Override
    public boolean accept(T component) {
        for (int i = 0; i < filters.size; i++) {
            if (!filters.get(i).accept(component))
                return false;
        }
        return true;
    }
}
