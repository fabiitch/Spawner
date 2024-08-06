package com.github.fabiitch.spawner.entity.mapper;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.github.fabiitch.spawner.entity.EntityWrapper;
import com.github.fabiitch.spawner.factory.Factory;

public class EntityMapper<W extends EntityWrapper> {

    private Array<ComponentFiller<W, ?>> fillers = new Array<>();
    private IntMap<W> entities = new IntMap<>();
    private Factory<W> wrapperFactory;

    public W get(int entityId) {

        W entityWrapper = entities.get(entityId);
        if (entityWrapper == null) {
            newWrapper(entityId);
        }
        return null;
    }

    public W newWrapper(int entityId) {
        W entityWrapper = wrapperFactory.getNew();
        for (ComponentFiller<W, ?> filler : fillers) {
            filler.fill(entityWrapper);
        }
        return entityWrapper;
    }

    public void registerMapper() {

    }
}
