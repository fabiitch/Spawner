package com.github.fabiitch.spawner.entity;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.attack.PoisonAuraComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.data.components.move.MoveComponent;
import com.github.fabiitch.spawner.data.components.move.WalkMoveComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrototypeTest extends BaseTest {

    @Test
    public void prototypeCreationTest() {
        int entityId = -1;
        Prototype prototype = new Prototype();

        // Class test

        prototype.addComponent(new SwordComponent(50));
        entityId = world.createEntity(prototype);
        assertTrue(swordMapper.hasComponent(entityId));
        assertTrue(attackBehaviorMapper.hasBehavior(entityId));

        prototype.removeComponent(SwordComponent.class);
        entityId = world.createEntity(prototype);
        assertFalse(swordMapper.hasComponent(entityId));
        assertFalse(attackBehaviorMapper.hasBehavior(entityId));

        //---- interface test
        prototype.addComponent(new WalkMoveComponent(), MoveComponent.class);
        entityId = world.createEntity(prototype);
        assertTrue(moveMapper.hasComponent(entityId));

        prototype.removeComponent(MoveComponent.class);
        entityId = world.createEntity(prototype);
        assertFalse(swordMapper.hasComponent(entityId));

        // Remove by objectTest
        {
            SwordComponent swordComponent = new SwordComponent(50);
            prototype.addComponent(swordComponent);
            prototype.removeComponent(swordComponent);
            entityId = world.createEntity(prototype);
            assertFalse(swordMapper.hasComponent(entityId));
        }


        //test add multiple
        prototype = new Prototype();
        prototype.addComponent(new SwordComponent(50));
        prototype.addComponent(new WalkMoveComponent(), MoveComponent.class);
        prototype.addComponent(new PoisonAuraComponent());

        entityId = world.createEntity(prototype);
        assertTrue(swordMapper.hasComponent(entityId));
        assertTrue(moveMapper.hasComponent(entityId));
        assertTrue(poisonAuraMapper.hasComponent(entityId));
        assertTrue(attackBehaviorMapper.hasBehavior(entityId));


    }
}
