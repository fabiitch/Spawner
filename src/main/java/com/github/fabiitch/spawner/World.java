package com.github.fabiitch.spawner;

import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.ArchetypeManager;
import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;
import com.github.fabiitch.spawner.behavior.BehaviorManager;
import com.github.fabiitch.spawner.behavior.BehaviorMapper;
import com.github.fabiitch.spawner.component.ComponentManager;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.entity.EntityManager;
import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.family.FamilyManager;
import com.github.fabiitch.spawner.flag.FlagManager;
import com.github.fabiitch.spawner.flag.FlagMapper;
import com.github.fabiitch.spawner.listeners.ListenerManager;
import com.github.fabiitch.spawner.pools.SpawnerGdxPools;
import com.github.fabiitch.spawner.pools.SpawnerPools;
import com.github.fabiitch.spawner.signals.SignalDataManager;
import com.github.fabiitch.spawner.systems.EcsSystem;
import com.github.fabiitch.spawner.systems.SystemManager;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import com.github.fabiitch.spawner.wrapper.EntityWrapperManager;
import lombok.Getter;

import java.util.Map;

public class World {

    @Getter
    private final WorldConfig config;
    @Getter
    private final SpawnerPools pools;

    private final SystemManager systemManager;
    private final ComponentManager componentManager;
    private final FlagManager flagManager;
    private final EntityManager entityManager;
    private final BehaviorManager behaviorManager;
    private final ArchetypeManager archetypeManager;
    private final FamilyManager familyManager;
    private final ListenerManager listenerManager;
    private final EntityWrapperManager entityWrapperManager;
    private final SignalDataManager signalDataManager;

    @Getter
    private boolean updating;

    public World() {
        this(new SpawnerGdxPools());
    }

    public World(SpawnerPools pools) {
        this.pools = pools;
        entityManager = new EntityManager();
        entityWrapperManager = new EntityWrapperManager();
        familyManager = new FamilyManager(entityManager);
        listenerManager = new ListenerManager(entityManager, familyManager, entityWrapperManager);
        flagManager = new FlagManager(listenerManager);
        behaviorManager = new BehaviorManager(listenerManager);
        componentManager = new ComponentManager(listenerManager, behaviorManager);
        signalDataManager = new SignalDataManager(componentManager, behaviorManager);

        archetypeManager = new ArchetypeManager(entityManager, componentManager, behaviorManager, flagManager);
        systemManager = new SystemManager(this);

        this.config = new WorldConfig(componentManager, behaviorManager, flagManager,
                archetypeManager, familyManager, systemManager, listenerManager,
                entityWrapperManager, signalDataManager);
    }

    public void update(float dt) {
        updating = true;
        updateLoop(dt);
        updating = false;
    }

    protected void updateLoop(float dt) {
        EcsSystem nextSystem = systemManager.loop();
        if (nextSystem != null) {
            nextSystem.update(dt);
            this.updateLoop(dt);
        }
    }

    public int createEntity() {
        int entityId = entityManager.create();
        listenerManager.onEntityCreate(entityId);
        return entityId;
    }

    public boolean hasEntity(int entityId) {
        return entityManager.exist(entityId);
    }

    public int createEntity(Prototype prototype) {
        int entityId = entityManager.create();
        for (Map.Entry<Class<?>, Object> componentEntry : prototype.getComponentsMap().entrySet()) {
            ComponentMapper mapper = componentManager.getMapper(componentEntry.getKey());
            mapper.addComponent(entityId, componentEntry.getValue());
        }

        Bits flagBits = prototype.getFlagBits();
        int nextSetBit = flagBits.nextSetBit(0);
        while (nextSetBit != -1) {
            FlagMapper mapper = flagManager.getMapper(nextSetBit);
            mapper.setFlag(entityId);
            nextSetBit = flagBits.nextSetBit(++nextSetBit);
        }

        listenerManager.onEntityCreate(entityId);
        return entityId;
    }

    public boolean destroyEntity(int entityId) {
        Bits componentBits = entityManager.getComponentBits(entityId);
        if (componentBits == null)
            return false;

        componentManager.destroyEntity(entityId, componentBits);
        behaviorManager.destroyEntity(entityId, entityManager.getBehaviorBits(entityId));
        flagManager.destroyEntity(entityId, entityManager.getFlagBits(entityId));
        familyManager.onEntityRemove(entityId);
        listenerManager.onEntityDestroy(entityId);
        entityManager.destroy(entityId);
        return true;
    }

    public EntityReference removeEntity(int entityId) {
        Bits componentsBits = entityManager.getComponentBits(entityId);
        if (componentsBits == null)
            return null;

        Bits behaviorBits = entityManager.getBehaviorBits(entityId);
        Bits flagBits = entityManager.getFlagBits(entityId);

        EntityReference entityReference = new EntityReference(); // TODO pool
        entityReference.setEntityId(entityId);
        entityReference.setComponentBits(componentsBits);
        entityReference.setFlagBits(flagBits);
        entityReference.setBehaviorBits(behaviorBits);

        listenerManager.onEntityRemove(entityId);
        familyManager.onEntityRemove(entityId);

        componentManager.removeEntity(entityReference);
        flagManager.destroyEntity(entityId, flagBits);

        entityManager.remove(entityId);
        return entityReference;
    }

    public boolean addEntity(EntityReference entityReference) {
        int entityId = entityReference.getEntityId();
        boolean add = entityManager.add(entityReference);
        if (add) {
            componentManager.addEntity(entityReference);
            flagManager.addEntity(entityReference);

            listenerManager.onEntityAdd(entityId);
            familyManager.onEntityAdd(entityId);
        }
        return add;
    }

    public <T> ComponentMapper<T> getComponentMapper(Class<T> componentClass) {
        return componentManager.getMapper(componentClass);
    }

    public <T> ComponentMapper<T> getComponentMapper(int mapperIndex) {
        return componentManager.getMapper(mapperIndex);
    }

    public <T> BehaviorMapper<T> getBehaviorMapper(Class<T> componentClass) {
        return behaviorManager.getMapper(componentClass);
    }

    public <T> BehaviorMapper<T> getBehaviorMapper(int mapperIndex) {
        return behaviorManager.getMapper(mapperIndex);
    }

    public FlagMapper getFlagMapper(Class<?> flagClass) {
        return flagManager.getMapper(flagClass);
    }

    public FlagMapper getFlagMapper(String flagName) {
        return flagManager.getMapper(flagName);
    }


    public <E extends Enum> FlagMapper getFlagMapper(E enumValue) {
        return flagManager.getMapper(enumValue.name());
    }


    public FlagMapper getFlagMapper(int mapperIndex) {
        return flagManager.getMapper(mapperIndex);
    }

    public SafeIntArray getEntities() {
        return entityManager.getEntities();
    }

    public IntArray getEntities(EntityMatcher entityMatcher, IntArray res) {
        SafeIntArray worldEntities = getEntities();

        for (int i = 0; i < worldEntities.size(); i++) {
            int entityIds = worldEntities.get(i);
            if (entityMatcher.accept(entityIds)) {
                res.add(entityIds);
            }
        }
        return res;
    }

}
