package com.github.fabiitch.spawner;

import com.github.fabiitch.spawner.archetype.Archetype;
import com.github.fabiitch.spawner.archetype.ArchetypeBuilder;
import com.github.fabiitch.spawner.archetype.ArchetypeManager;
import com.github.fabiitch.spawner.behavior.BehaviorManager;
import com.github.fabiitch.spawner.behavior.BehaviorMapper;
import com.github.fabiitch.spawner.component.ComponentManager;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.entity.mapper.EntityMapper;
import com.github.fabiitch.spawner.entity.mapper.EntityMapperManager;
import com.github.fabiitch.spawner.family.Family;
import com.github.fabiitch.spawner.family.FamilyManager;
import com.github.fabiitch.spawner.flag.FlagManager;
import com.github.fabiitch.spawner.flag.FlagMapper;
import com.github.fabiitch.spawner.listeners.ListenerManager;
import com.github.fabiitch.spawner.listeners.WorldListener;
import com.github.fabiitch.spawner.listeners.entity.EntityListener;
import com.github.fabiitch.spawner.systems.EcsSystem;
import com.github.fabiitch.spawner.systems.EntitySystem;
import com.github.fabiitch.spawner.systems.SystemManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WorldConfig {
    private final ComponentManager componentManager;
    private final BehaviorManager behaviorManager;
    private final FlagManager flagManager;
    private final ArchetypeManager archetypeManager;
    private final FamilyManager familyManager;
    private final SystemManager systemManager;
    private final ListenerManager listenerManager;
    private final EntityMapperManager entityMapperManager;


    public ComponentMapper<?> registerComponent(Class<?> componentClass) {
        ComponentMapper<?> componentMapper = componentManager.registerComponent(componentClass);
        return componentMapper;
    }

    public WorldConfig registerComponents(Class<?>... componentsClass) {
        for (Class<?> componentClass : componentsClass) {
            registerComponent(componentClass);
        }
        return this;
    }

    public BehaviorMapper registerBehavior(Class<?> behaviorClass) {
        BehaviorMapper behaviorMapper = behaviorManager.registerBehavior(behaviorClass);
        return behaviorMapper;
    }

    public WorldConfig registerBehaviors(Class<?>... behaviorsClass) {
        for (Class<?> behaviorClass : behaviorsClass) {
            registerBehavior(behaviorClass);
        }
        return this;
    }

    public FlagMapper registerFlag(Class<?> flagClass) {
        FlagMapper mapper = flagManager.registerFlag(flagClass);
        return mapper;
    }

    public void registerFlags(Class<?>... flagsClass) {
        for (Class<?> flagClass : flagsClass) {
            registerFlag(flagClass);
        }
    }

    public Archetype registerArchetype(ArchetypeBuilder archetypeBuilder) {
        return archetypeManager.build(archetypeBuilder);
    }

    public Family registerFamily(ArchetypeBuilder archetypeBuilder) {
        Archetype archetype = archetypeManager.build(archetypeBuilder);
        Family family = new Family(archetype);
        familyManager.addFamily(family);
        return family;
    }

    public Family registerFamily(Family family) {
        familyManager.addFamily(family);
        return family;
    }

    public void registerEntityMapper(EntityMapper<?> entityMapper) {
        entityMapperManager.addMapper(entityMapper);
    }

    public void registerEntityMappers(EntityMapper<?>... entityMappers) {
        for (EntityMapper<?> entityMapper : entityMappers) {
            registerEntityMapper(entityMapper);
        }
    }

    public WorldConfig addSystem(EcsSystem... systems) {
        for (EcsSystem system : systems)
            if (system instanceof EntitySystem) {
                EntitySystem entitySystem = (EntitySystem) system;
                familyManager.addFamily(entitySystem.getFamily());
                systemManager.addSystem(system);
            }
        return this;
    }

    public boolean removeSystem(EcsSystem system) {
        return systemManager.removeSystem(system);
    }

    public boolean removeSystem(EntitySystem system) {
        familyManager.remove(system.getFamily());
        return systemManager.removeSystem(system);
    }

    public void addEngineListener(WorldListener worldListener) {
        listenerManager.addEngineListener(worldListener);
    }

    public void removeEngineListener(WorldListener worldListener) {
        listenerManager.removeEngineListener(worldListener);
    }

    public void addEntityListener(int entityId, EntityListener entityListener) {
        listenerManager.addEntityListener(entityId, entityListener);
    }

    public void removeEntityListener(int entityId, EntityListener entityListener) {
        listenerManager.removeEntityListener(entityId, entityListener);
    }

}
