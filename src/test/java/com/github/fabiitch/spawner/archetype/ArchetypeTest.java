package com.github.fabiitch.spawner.archetype;

import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;
import com.github.fabiitch.spawner.data.behaviors.DefenseBehavior;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.data.components.attack.KnifeComponent;
import com.github.fabiitch.spawner.data.components.attack.PoisonAuraComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.data.components.defense.ArmorComponent;
import com.github.fabiitch.spawner.data.components.defense.ShieldComponent;
import com.github.fabiitch.spawner.data.components.move.FlyMoveComponent;
import com.github.fabiitch.spawner.data.components.move.MoveComponent;
import com.github.fabiitch.spawner.data.flags.DeathFlag;
import com.github.fabiitch.spawner.data.flags.OutFlag;
import org.junit.jupiter.api.Test;

import static com.github.fabiitch.spawner.archetype.criteria.QueryCriteria.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArchetypeTest extends BaseTest {

    @Test
    public void oneComponentAcceptTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get().components(OneOf, SwordComponent.class, KnifeComponent.class);

        Archetype archetype = config.registerArchetype(archetypeBuilder);

        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));

            prototype.removeComponent(SwordComponent.class);
            entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new KnifeComponent());
            int entity = world.createEntity(prototype);
            assertTrue(archetype.accept(entity));
        }
    }


    @Test
    public void allComponentAcceptTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get().components(AllOf, SwordComponent.class, KnifeComponent.class);
        Archetype archetype = config.registerArchetype(archetypeBuilder);

        Prototype prototype = new Prototype();
        prototype.addComponent(new SwordComponent(10));
        {
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }

        prototype.addComponent(new KnifeComponent());
        {
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));
        }

        prototype.removeComponent(KnifeComponent.class);
        {
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
    }

    @Test
    public void oneComponentExcludeTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .components(NoneOf, SwordComponent.class, KnifeComponent.class);

        Archetype archetype = config.registerArchetype(archetypeBuilder);
        {
            int entityId = world.createEntity();
            assertTrue(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            int entity = world.createEntity(prototype);
            assertFalse(archetype.accept(entity));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            prototype.addComponent(new KnifeComponent());
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));

            swordMapper.removeComponent(entityId);
            assertFalse(archetype.accept(entityId));

            knifeMapper.removeComponent(entityId);
            assertTrue(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new PoisonAuraComponent());
            prototype.addComponent(new KnifeComponent());
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new PoisonAuraComponent());
            prototype.addComponent(new PositionComponent());
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));
        }
    }

    @Test
    public void allComponentExcludeTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .components(NoneAll, SwordComponent.class, PositionComponent.class);
        Archetype archetype = config.registerArchetype(archetypeBuilder);

        {
            int entityId = world.createEntity();
            assertTrue(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            int entity = world.createEntity(prototype);
            assertTrue(archetype.accept(entity));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            prototype.addComponent(new KnifeComponent());
            prototype.addComponent(new PositionComponent());
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new PoisonAuraComponent());
            prototype.addComponent(new KnifeComponent());
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));
        }
    }

    @Test
    public void oneBehaviorAcceptTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get().behaviors(OneOf, AttackBehavior.class);
        Archetype archetype = config.registerArchetype(archetypeBuilder);

        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));

            prototype.removeComponent(SwordComponent.class);
            entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new KnifeComponent());
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new KnifeComponent());
            prototype.addComponent(new PositionComponent());
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new PositionComponent());
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
    }

    @Test
    public void allBehaviorAcceptTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get().behaviors(AllOf, AttackBehavior.class, DefenseBehavior.class);

        Archetype archetype = config.registerArchetype(archetypeBuilder);
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            prototype.addComponent(new ShieldComponent());
            int entity = world.createEntity(prototype);
            assertTrue(archetype.accept(entity));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new KnifeComponent());
            prototype.addComponent(new SwordComponent(10));
            prototype.addComponent(new ShieldComponent());
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new PoisonAuraComponent());
            prototype.addComponent(new ArmorComponent());
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));
        }
    }

    @Test
    public void oneBehaviorExcludeTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .behaviors(NoneOf, AttackBehavior.class, DefenseBehavior.class);

        Archetype archetype = config.registerArchetype(archetypeBuilder);
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            prototype.addComponent(new ShieldComponent());
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new KnifeComponent());
            prototype.addComponent(new PositionComponent());
            prototype.addComponent(new ShieldComponent());
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new PositionComponent());
            prototype.addComponent(new FlyMoveComponent(), MoveComponent.class);
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));
        }
    }

    @Test
    public void allBehaviorExcludeTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .behaviors(NoneAll, AttackBehavior.class, DefenseBehavior.class);

        Archetype archetype = config.registerArchetype(archetypeBuilder);
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(10));
            prototype.addComponent(new ShieldComponent());
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new KnifeComponent());
            prototype.addComponent(new PositionComponent());
            prototype.addComponent(new ShieldComponent());
            int entityId = world.createEntity(prototype);
            assertFalse(archetype.accept(entityId));
        }
        {
            Prototype prototype = new Prototype();
            prototype.addComponent(new ShieldComponent());
            prototype.addComponent(new PositionComponent());
            prototype.addComponent(new FlyMoveComponent(), MoveComponent.class);
            int entityId = world.createEntity(prototype);
            assertTrue(archetype.accept(entityId));
        }
    }

    @Test
    public void oneFlagAcceptTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .flags(OneOf, DeathFlag.class, OutFlag.class);

        Archetype archetype = config.registerArchetype(archetypeBuilder);

        int entityId = world.createEntity();
        assertFalse(archetype.accept(entityId)); //no flag

        deathFlagMapper.setFlag(entityId);
        assertTrue(archetype.accept(entityId)); //death present

        outFlagMapper.setFlag(entityId);
        assertTrue(archetype.accept(entityId)); //all present

        deathFlagMapper.removeFlag(entityId);
        assertTrue(archetype.accept(entityId)); //only out

        outFlagMapper.setFlag(entityId); //reput out
        assertTrue(archetype.accept(entityId));

        outFlagMapper.removeFlag(entityId); //remove out
        assertFalse(archetype.accept(entityId));
    }

    @Test
    public void allFlagAcceptTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .flags(AllOf, DeathFlag.class, OutFlag.class);

        Archetype archetype = config.registerArchetype(archetypeBuilder);

        int entityId = world.createEntity();
        assertFalse(archetype.accept(entityId));

        deathFlagMapper.setFlag(entityId);
        assertFalse(archetype.accept(entityId));

        outFlagMapper.setFlag(entityId);
        assertTrue(archetype.accept(entityId));

        deathFlagMapper.removeFlag(entityId);
        assertFalse(archetype.accept(entityId));

        outFlagMapper.setFlag(entityId);
        assertFalse(archetype.accept(entityId));
    }

    @Test
    public void oneFlagExcludeTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .flags(NoneOf, DeathFlag.class, OutFlag.class);

        Archetype archetype = config.registerArchetype(archetypeBuilder);

        int entityId = world.createEntity();
        assertTrue(archetype.accept(entityId));

        deathFlagMapper.setFlag(entityId);
        assertFalse(archetype.accept(entityId));

        outFlagMapper.setFlag(entityId);
        assertFalse(archetype.accept(entityId));

        deathFlagMapper.removeFlag(entityId);
        assertFalse(archetype.accept(entityId));

        outFlagMapper.setFlag(entityId); //reput out
        assertFalse(archetype.accept(entityId));

        outFlagMapper.removeFlag(entityId); //remove out
        assertTrue(archetype.accept(entityId));
    }

    @Test
    public void allFlagExcludeTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .flags(NoneAll, DeathFlag.class, OutFlag.class);

        Archetype archetype = config.registerArchetype(archetypeBuilder);

        int entityId = world.createEntity();
        assertTrue(archetype.accept(entityId));

        deathFlagMapper.setFlag(entityId);
        assertTrue(archetype.accept(entityId));

        outFlagMapper.setFlag(entityId);
        assertFalse(archetype.accept(entityId));

        deathFlagMapper.removeFlag(entityId);
        assertTrue(archetype.accept(entityId));

        outFlagMapper.setFlag(entityId);
        assertTrue(archetype.accept(entityId));
    }
}
