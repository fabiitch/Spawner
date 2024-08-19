package com.github.fabiitch.spawner.groups;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;

public class EntityGroup implements EntityMatcher {

    private IntArray entities;

    private EntityMatcher matcher;



    @Override
    public boolean accept(int entityId) {
        return matcher.accept(entityId);
    }
}
