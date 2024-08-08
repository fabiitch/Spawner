package com.github.fabiitch.spawner.family;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.archetype.Archetype;
import com.github.fabiitch.spawner.archetype.ArchetypeBuilder;
import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;
import com.github.fabiitch.spawner.data.components.attack.KnifeComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.data.flags.DeathFlag;
import com.github.fabiitch.spawner.data.flags.OutFlag;
import com.github.fabiitch.spawner.sort.EntityComparator;
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

    @Test
    public void orderedTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get().behaviors(OneOf, AttackBehavior.class);
        Archetype archetype = config.registerArchetype(archetypeBuilder);

        Family family = new Family(archetype);
        config.registerFamily(family);

        int entityA = world.createEntity();
        int entityB = world.createEntity();
        int entityC = world.createEntity();
        int entityD = world.createEntity();

        EntityComparator comparator = (o1, o2) -> {
            AttackBehavior attackEntity1 = attackBehaviorMapper.getBehaviors(o1).getFirst();
            AttackBehavior attackEntity2 = attackBehaviorMapper.getBehaviors(o2).getFirst();
            return Integer.compare(attackEntity1.attack(), attackEntity2.attack());
        };

        swordMapper.addComponent(entityA, new SwordComponent(10));
        knifeMapper.addComponent(entityB, new KnifeComponent(1));
        knifeMapper.addComponent(entityC, new KnifeComponent(2));
        swordMapper.addComponent(entityD, new SwordComponent(30));

        family.sort(comparator);
        IntArray entities = family.getEntities();
        Assertions.assertEquals(entityB, entities.get(0));  //  1
        Assertions.assertEquals(entityC, entities.get(1));  //  2
        Assertions.assertEquals(entityA, entities.get(2));  //  10
        Assertions.assertEquals(entityD, entities.get(3));  //  30


        swordMapper.getComponent(entityD).setDamage(0);
        swordMapper.getComponent(entityA).setDamage(1000);


        family.sort(comparator);
        Assertions.assertEquals(entityD, entities.get(0));
        Assertions.assertEquals(entityB, entities.get(1));
        Assertions.assertEquals(entityC, entities.get(2));
        Assertions.assertEquals(entityA, entities.get(3));

    }
}
