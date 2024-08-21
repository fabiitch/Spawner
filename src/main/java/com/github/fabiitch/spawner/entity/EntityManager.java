package com.github.fabiitch.spawner.entity;

import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.utils.collections.IntDeque;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import com.github.fabiitch.spawner.utils.collections.Tab;
import com.github.fabiitch.spawner.utils.pools.BitsPool;

public class EntityManager {

    private int increment = 0;
    private final IntArray entities = new IntArray();
    private final SafeIntArray res = new SafeIntArray(entities);
    private final Tab<Bits> componentBits = new Tab<>();
    private final Tab<Bits> behaviorBits = new Tab<>();
    private final Tab<Bits> aspectBits = new Tab<>();
    private final Tab<Bits> flagBits = new Tab<>();
    private final IntDeque recycledIds = new IntDeque();
    private final BitsPool bitsPool = new BitsPool();

    public int create() {
        int entityId;
        if (recycledIds.isEmpty()) {
            entityId = increment++;
        } else {
            entityId = recycledIds.popFirst();
        }
        initEntity(entityId);

        entities.add(entityId);
        return entityId;
    }

    private void initEntity(int entityId) {
        componentBits.set(entityId, bitsPool.obtain());
        behaviorBits.set(entityId, bitsPool.obtain());
        flagBits.set(entityId, bitsPool.obtain());
        aspectBits.set(entityId, bitsPool.obtain());
    }

    public boolean destroy(int entityId) {
        boolean remove = remove(entityId);
        if (remove)
            recycledIds.add(entityId);
        return remove;
    }

    public boolean remove(int entityId) {
        Bits bits = componentBits.get(entityId);
        if (bits == null)
            return false;

        bitsPool.free(componentBits.getUnsafe(entityId));
        bitsPool.free(behaviorBits.getUnsafe(entityId));
        bitsPool.free(aspectBits.getUnsafe(entityId));
        bitsPool.free(flagBits.getUnsafe(entityId));

        componentBits.setUnsafe(entityId, null);
        behaviorBits.setUnsafe(entityId, null);
        aspectBits.setUnsafe(entityId, null);
        flagBits.setUnsafe(entityId, null);

        entities.removeValue(entityId);
        return true;
    }

    public boolean add(EntityReference entityReference) {
        int entityId = entityReference.getEntityId();
        if (exist(entityId))
            return false;

        Bits entityComponentBits = bitsPool.obtain();
        entityComponentBits.or(entityReference.getComponentBits());
        componentBits.set(entityId, entityComponentBits);

        Bits entityBehaviorBits = bitsPool.obtain();
        entityBehaviorBits.or(entityReference.getBehaviorBits());
        behaviorBits.set(entityId, entityBehaviorBits);

        Bits entityAspectBits = bitsPool.obtain();
        entityAspectBits.or(entityReference.getAspectBits());
        aspectBits.set(entityId, entityAspectBits);

        Bits entityFlagBits = bitsPool.obtain();
        entityFlagBits.or(entityReference.getFlagBits());
        flagBits.set(entityId, entityFlagBits);

        entities.add(entityId);
        return true;
    }

    public Bits getComponentBits(int entityId) {
        return componentBits.get(entityId);
    }

    public Bits getBehaviorBits(int entityId) {
        return behaviorBits.get(entityId);
    }

    public Bits getAspectBits(int entityId) {
        return aspectBits.get(entityId);
    }

    public Bits getFlagBits(int entityId) {
        return flagBits.get(entityId);
    }

    public boolean exist(int entityId) {
        return getComponentBits(entityId) != null;
    }

    public void onComponentAdd(int entityId, int componentIndex) {
        componentBits.get(entityId).set(componentIndex);
    }

    public void onComponentRemove(int entityId, int componentIndex) {
        componentBits.get(entityId).clear(componentIndex);
    }

    public void onFlagAdd(int entityId, int flagIndex) {
        flagBits.get(entityId).set(flagIndex);
    }

    public void onFlagRemove(int entityId, int flagIndex) {
        flagBits.get(entityId).clear(flagIndex);
    }

    public void onBehaviorGet(int entityId, int behaviorIndex) {
        behaviorBits.get(entityId).set(behaviorIndex);
    }

    public void onBehaviorLoose(int entityId, int behaviorIndex) {
        behaviorBits.get(entityId).clear(behaviorIndex);
    }


    public SafeIntArray getEntities() {
        return res;
    }
}
