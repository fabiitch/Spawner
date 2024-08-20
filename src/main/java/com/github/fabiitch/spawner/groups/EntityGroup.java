package com.github.fabiitch.spawner.groups;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.criteria.BehaviorImpacted;
import com.github.fabiitch.spawner.archetype.criteria.ComponentImpacted;
import com.github.fabiitch.spawner.query.EntityFilter;

public class EntityGroup implements EntityFilter, ComponentImpacted, BehaviorImpacted {

    private IntArray entities;

    private EntityFilter matcher;


    @Override
    public boolean accept(int entityId) {
        return matcher.accept(entityId);
    }
}
