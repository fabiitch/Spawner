package com.github.fabiitch.spawner.entity;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityManagerTest extends BaseTest {

    @Test
    public void getEntitiesTest() {

        int entityA = world.createEntity();
        int entityB = world.createEntity();

        assertContainsOnly(world.getEntities(), entityA, entityB);

        int entityC = world.createEntity();

        assertContainsOnly(world.getEntities(), entityA, entityB, entityC);

        EntityReference entityReferenceB = world.removeEntity(entityB);
        assertContainsOnly(world.getEntities(), entityA, entityC);

        world.addEntity(entityReferenceB);
        assertContainsOnly(world.getEntities(), entityA, entityB, entityC);

        world.destroyEntity(entityC);
        assertContainsOnly(world.getEntities(), entityA, entityB);

    }

    private void assertContainsOnly(SafeIntArray array, int... values) {
        Assertions.assertEquals(array.size(), values.length);
        for (int value : values)
            Assertions.assertTrue(array.contains(value));
    }
}
