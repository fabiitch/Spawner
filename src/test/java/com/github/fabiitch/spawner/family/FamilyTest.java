package com.github.fabiitch.spawner.family;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.Archetype;
import com.github.fabiitch.spawner.archetype.ArchetypeBuilder;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.attack.KnifeComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.data.flags.DeathFlag;
import com.github.fabiitch.spawner.data.flags.OutFlag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.fabiitch.spawner.archetype.criteria.QueryCriteria.AllOf;
import static com.github.fabiitch.spawner.archetype.criteria.QueryCriteria.OneOf;

public class FamilyTest extends BaseTest {

    @Test
    public void componentTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get().components(OneOf, SwordComponent.class, KnifeComponent.class);
        Archetype archetype = config.registerArchetype(archetypeBuilder);

        Family family = new Family(archetype);
        config.registerFamily(family);

        int entityA = world.createEntity();
        int entityB = world.createEntity();
        int entityC = world.createEntity();

        swordMapper.addComponent(entityA, new SwordComponent(55));
        swordMapper.addComponent(entityC, new SwordComponent(550));

        Assertions.assertTrue(family.hasEntities(entityA, entityC));
        Assertions.assertFalse(family.hasEntity(entityB));

        //remove
        swordMapper.removeComponent(entityC);
        Assertions.assertFalse(family.hasEntity(entityC));

        IntArray entities = family.getEntities();
        Assertions.assertTrue(entities.contains(entityA));
        Assertions.assertFalse(entities.contains(entityB));
        Assertions.assertFalse(entities.contains(entityC));
    }

    @Test
    public void flagTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get().flags(AllOf, OutFlag.class, DeathFlag.class);
        Archetype archetype = config.registerArchetype(archetypeBuilder);

        Family family = new Family(archetype);


        int entityA = world.createEntity();
        int entityB = world.createEntity();

        deathFlagMapper.setFlag(entityB);
        outFlagMapper.setFlag(entityB);

        //test register after
        config.registerFamily(family);

        Assertions.assertFalse(family.hasEntity(entityA));
        Assertions.assertTrue(family.hasEntity(entityB));

        //add all flag on entityA
        deathFlagMapper.setFlag(entityA);
        outFlagMapper.setFlag(entityA);

        Assertions.assertTrue(family.hasEntity(entityA));
        Assertions.assertTrue(family.hasEntity(entityB));

        // remove one flag of entityA
        deathFlagMapper.removeFlag(entityA);
        // remove one flag of entityB
        outFlagMapper.removeFlag(entityB);

        Assertions.assertFalse(family.hasEntity(entityA));
        Assertions.assertFalse(family.hasEntity(entityB));
    }
}
