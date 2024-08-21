package com.github.fabiitch.spawner.aspect;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.IntMap;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.impact.AspectImpacted;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.listeners.aspect.AspectListener;
import com.github.fabiitch.spawner.listeners.component.ComponentListener;
import com.github.fabiitch.spawner.utils.mappers.ObjectMapper;

public class AspectMapper<A> extends ObjectMapper<A, AspectListener<A>>
        implements ComponentListener<A>, AspectImpacted, ComponentImpacted {

    private final Array<ComponentMapper<A>> mappers = new Array<>();
    private final IntIntMap entityMap = new IntIntMap();
    private final IntMap<ComponentMapper<A>> mapperMaps = new IntMap<>();

    public AspectMapper(int index) {
        super(index);
    }

    public A getAspect(int entityId) {
        int indexComponent = entityMap.get(entityId, -1);
        if (indexComponent > -1) {
            return mapperMaps.get(indexComponent).getComponent(entityId);
        }
        return null;
    }

    public boolean hasAspect(int entityId) {
        return entityMap.get(entityId, -1) > -1;
    }

    public A removeAspect(int entityId) {
        int indexComponent = entityMap.get(entityId, -1);
        if (indexComponent > -1) {
            return mapperMaps.get(indexComponent).removeComponent(entityId);
        }
        return null;
    }

    public void updated(int entityId) {
        int indexComponent = entityMap.get(entityId, -1);
        ComponentMapper<A> componentMapper = mapperMaps.get(indexComponent);
        A component = mapperMaps.get(indexComponent).getComponent(entityId);
        for (AspectListener<A> listener : listeners) {
            listener.onAspectUpdated(entityId, component, this.index, componentMapper.getIndex());
        }
    }


    @Override
    public void onComponentAdd(int entityId, A component, int componentIndex) {
        int oldComponentIndex = entityMap.get(entityId, -1);
        if (oldComponentIndex > -1)
            mapperMaps.get(oldComponentIndex).removeComponent(entityId);

        entityMap.put(entityId, componentIndex);

    }

    @Override
    public void onComponentRemove(int entityId, A component, int componentIndex) {
        int oldComponentIndex = entityMap.get(entityId, -1);
        if (oldComponentIndex > -1)
            mapperMaps.get(oldComponentIndex).removeComponent(entityId);
    }

    @Override
    public void onComponentUpdate(int entityId, A component, int componentIndex) {

    }

    void addInternalListener(AspectListener listener) {
        internalListeners.add(listener);
    }

    void removeInternalListener(AspectListener listener) {
        internalListeners.removeValue(listener, true);
    }

    @Override
    public boolean impactedByAspect(int aspectIndex) {
        return aspectIndex == this.index;
    }

    @Override
    public boolean impactedByComponent(int indexComponent) { //TODO
        return false;
    }
}
