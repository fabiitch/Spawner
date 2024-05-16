package com.github.fabiitch.spawner.entity;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.data.components.attack.PoisonAuraComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityReferenceTest extends BaseTest {

    @Test
    public void removeAddTest() {
        int entity = world.createEntity();
        assertTrue(world.hasEntity(entity));

        swordMapper.addComponent(entity, new SwordComponent(500));
        positionMapper.addComponent(entity, new PositionComponent());
        assertTrue(swordMapper.hasComponent(entity));
        assertTrue(positionMapper.hasComponent(entity));

        EntityReference entityReference = world.removeEntity(entity);
        assertFalse(swordMapper.hasComponent(entity));
        assertFalse(positionMapper.hasComponent(entity));
        assertFalse(world.hasEntity(entity));

        world.addEntity(entityReference);
        assertTrue(world.hasEntity(entity));
        assertTrue(swordMapper.hasComponent(entity));
        assertTrue(positionMapper.hasComponent(entity));
    }

    @Test
    public void testModifyEntityRef() {
        int entityId = world.createEntity();
        swordMapper.addComponent(entityId, new SwordComponent(500));
        positionMapper.addComponent(entityId, new PositionComponent());

        EntityReference entityReference = world.removeEntity(entityId);

        entityReference.removeComponent(swordMapper.getIndex());
        entityReference.addComponent(poisonAuraMapper.getIndex(), new PoisonAuraComponent());
        assertFalse(poisonAuraMapper.hasComponent(entityId));

        world.addEntity(entityReference);

        assertTrue(world.hasEntity(entityId));
        assertFalse(swordMapper.hasComponent(entityId));
        assertTrue(positionMapper.hasComponent(entityId));
        assertTrue(poisonAuraMapper.hasComponent(entityId));
        assertEquals(1, attackBehaviorMapper.getBehaviors(entityId).size());
    }

    @Test
    public void checkIdStayEmpty() {
        int entity1 = world.createEntity();
        int entity2 = world.createEntity();
        int entity3 = world.createEntity();
        EntityReference entityReference1 = world.removeEntity(entity1);
        EntityReference entityReference2 = world.removeEntity(entity2);
        EntityReference entityReference3 = world.removeEntity(entity3);
        for (int i = 0; i < 100; i++)
            world.createEntity();

        assertFalse(world.hasEntity(entity1));
        assertFalse(world.hasEntity(entity2));
        assertFalse(world.hasEntity(entity3));
    }
}
