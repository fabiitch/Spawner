package com.github.fabiitch.spawner.archetype;

import com.github.fabiitch.spawner.archetype.builder.ArchetypeBuilder;
import com.github.fabiitch.spawner.archetype.builder.ArchetypeProcessor;
import com.github.fabiitch.spawner.aspect.AspectManager;
import com.github.fabiitch.spawner.behavior.BehaviorManager;
import com.github.fabiitch.spawner.component.ComponentManager;
import com.github.fabiitch.spawner.entity.EntityManager;
import com.github.fabiitch.spawner.flag.FlagManager;

public class ArchetypeManager {

    private ArchetypeProcessor processor;




    public ArchetypeManager(EntityManager entityManager, ComponentManager componentManager, BehaviorManager behaviorManager, AspectManager aspectManager, FlagManager flagManager) {
        this.processor = new ArchetypeProcessor(entityManager, componentManager, behaviorManager, aspectManager, flagManager);
    }

    public IArchetype build(ArchetypeBuilder archetypeBuilder) {
        IArchetype archetype = processor.build(archetypeBuilder);
        return archetype;
    }

}
