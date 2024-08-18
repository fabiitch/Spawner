package com.github.fabiitch.spawner.system.entity;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.World;
import com.github.fabiitch.spawner.archetype.Archetype;
import com.github.fabiitch.spawner.archetype.ArchetypeBuilder;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.systems.EntitySystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntitySystemAddRemoveDuringIterationTest extends BaseTest {


    @Test
    public void removeComponentDuringIteration() {
        int entity1, entity2, entity3;
        Archetype archetype = ArchetypeBuilder.get().hasComponent(PositionComponent.class).register(config);

        RemovePositionSystem removePositionSystem = new RemovePositionSystem(archetype);
        config.addSystem(removePositionSystem);

        entity1 = world.createEntity(new Prototype().addComponent(new PositionComponent(10, 10)));
        entity2 = world.createEntity(new Prototype().addComponent(new PositionComponent(0, 0)));
        entity3 = world.createEntity(new Prototype().addComponent(new PositionComponent(0, 0)));

        Assertions.assertEquals(3, removePositionSystem.getEntities().size);

        world.update(1);
        Assertions.assertEquals(0, removePositionSystem.getEntities().size);
    }

    @Test
    public void addComponentDuringIteration() {
        Archetype archetype = ArchetypeBuilder.get().hasComponent(PositionComponent.class).register(config);

        CreatePositionSystem createPositionSystem = new CreatePositionSystem(archetype, world);
        config.addSystem(createPositionSystem);

        for (int i = 0; i < 10; i++) {
            world.createEntity(new Prototype().addComponent(new PositionComponent()));
        }
        Assertions.assertEquals(10, createPositionSystem.getEntities().size);

        world.update(1);
        Assertions.assertEquals(20, createPositionSystem.getEntities().size);

        world.update(1);
        Assertions.assertEquals(40, createPositionSystem.getEntities().size);
    }


    private static class RemovePositionSystem extends EntitySystem {

        public RemovePositionSystem(Archetype archetype) {
            super(archetype, 0);
        }

        @Override
        protected void processEntity(int entityId, float dt) {
            positionMapper.removeComponent(entityId);
        }
    }

    private static class CreatePositionSystem extends EntitySystem {

        private World world;

        public CreatePositionSystem(Archetype archetype, World world) {
            super(archetype, 0);
            this.world = world;
        }

        @Override
        protected void processEntity(int entityId, float dt) {
            world.createEntity(new Prototype().addComponent(new PositionComponent()));
        }
    }
}
