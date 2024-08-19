package com.github.fabiitch.spawner.system.entity;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.data.components.defense.ShieldComponent;
import com.github.fabiitch.spawner.data.systems.SwordSystem;
import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntitySystemTest extends BaseTest {

    @Test
    public void testAddRemoveComponentFromEntity() {
        int entity1, entity2, entity3;
        SwordSystem swordSystem = new SwordSystem(swordArchetype);
        config.addSystem(swordSystem);

        {
            world.update(2);
            assertEquals(1, swordSystem.callCount);
            assertEquals(0, swordSystem.entityCallCount);
            assertEquals(0, swordSystem.getEntities().size());
        }

        {
            //should add entity 1 in system
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(50));
            entity1 = world.createEntity(prototype);

            world.update(2);
            assertEquals(2, swordSystem.callCount);
            assertEquals(1, swordSystem.entityCallCount);
            assertEquals(1, swordSystem.getEntities().size());
            arrayContains(swordSystem.getEntities(), entity1);
        }
        {
            //should add entity 2 in system
            Prototype prototype = new Prototype();
            prototype.addComponent(new SwordComponent(50));
            prototype.addComponent(new ShieldComponent());
            entity2 = world.createEntity(prototype);
            world.update(2);

            assertEquals(3, swordSystem.callCount);
            assertEquals(3, swordSystem.entityCallCount);
            assertEquals(2, swordSystem.getEntities().size());
            arrayContains(swordSystem.getEntities(), entity1, entity2);
        }
        {
            //should not add entity 3
            Prototype prototype = new Prototype();
            prototype.addComponent(new ShieldComponent());
            entity3 = world.createEntity(prototype);
            world.update(2);

            assertEquals(4, swordSystem.callCount);
            assertEquals(5, swordSystem.entityCallCount);
            assertEquals(2, swordSystem.getEntities().size());
            arrayContains(swordSystem.getEntities(), entity1, entity2);
            arrayNotContains(swordSystem.getEntities(), entity3);
        }
        {
            //should remove entity 2
            world.destroyEntity(entity2);
            world.update(2);
            assertEquals(5, swordSystem.callCount);
            assertEquals(6, swordSystem.entityCallCount);
            assertEquals(1, swordSystem.getEntities().size());
            arrayContains(swordSystem.getEntities(), entity1);
            arrayNotContains(swordSystem.getEntities(), entity2, entity3);
        }

        {
            //should remove entity 1
            swordMapper.removeComponent(entity1);
            world.update(2);
            assertEquals(6, swordSystem.callCount);
            assertEquals(6, swordSystem.entityCallCount);
            assertEquals(0, swordSystem.getEntities().size());
            arrayNotContains(swordSystem.getEntities(), entity1, entity2, entity3);
        }
    }


    private void arrayContains(SafeIntArray intArray, int... values) {
        for (int value : values) {
            assertTrue(intArray.contains(value));
        }
    }

    private void arrayNotContains(SafeIntArray intArray, int... values) {
        for (int value : values) {
            assertFalse(intArray.contains(value));
        }
    }
}
