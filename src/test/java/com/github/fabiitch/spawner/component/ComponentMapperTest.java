package com.github.fabiitch.spawner.component;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.data.components.move.FlyMoveComponent;
import com.github.fabiitch.spawner.data.components.move.MoveComponent;
import com.github.fabiitch.spawner.data.components.move.WalkMoveComponent;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComponentMapperTest extends BaseTest {

    @Test
    public void objectTest() {
        int entityId = world.createEntity();
        SwordComponent swordComponent;

        //has
        assertFalse(swordMapper.hasComponent(entityId));

        //add
        swordMapper.addComponent(entityId, new SwordComponent(10));
        swordComponent = swordMapper.getComponent(entityId);
        assertTrue(swordMapper.hasComponent(entityId));
        assertNotNull(swordComponent);

        //erase
        swordMapper.addComponent(entityId, new SwordComponent(20));
        swordComponent = swordMapper.getComponent(entityId);
        assertEquals(20, swordComponent.damage);

        //delete
        swordMapper.removeComponent(entityId);
        swordComponent = swordMapper.getComponent(entityId);
        assertNull(swordComponent);
    }

    //Interface is MoveComponent
    @Test
    public void interfaceTest() {
        int entityId = world.createEntity();
        MoveComponent moveComponent;

        //has
        assertFalse(swordMapper.hasComponent(entityId));

        //add
        moveMapper.addComponent(entityId, new WalkMoveComponent());
        assertTrue(moveMapper.hasComponent(entityId));
        moveComponent = moveMapper.getComponent(entityId);
        assertNotNull(moveComponent);

        //erase
        moveMapper.addComponent(entityId, new FlyMoveComponent());
        moveComponent = moveMapper.getComponent(entityId);
        assertEquals(100, moveComponent.getSpeed());

        //delete
        moveMapper.removeComponent(entityId);
        moveComponent = moveMapper.getComponent(entityId);
        assertNull(moveComponent);
    }

    @Test
    public void getAllTest() {
        int entityIdA = world.createEntity();
        int entityIdB = world.createEntity();
        int entityIdC = world.createEntity();
        int entityIdD = world.createEntity();

        swordMapper.addComponent(entityIdA, new SwordComponent(10));
        swordMapper.addComponent(entityIdC, new SwordComponent(50));
        swordMapper.addComponent(entityIdD, new SwordComponent(50));
        swordMapper.removeComponent(entityIdD);

        SafeTab<SwordComponent> all = swordMapper.getAll();
        assertEquals(10, all.get(entityIdA).damage);
        assertEquals(50, all.get(entityIdC).damage);
        assertNull(all.get(entityIdB));
        assertNull(all.get(entityIdD));
    }


}
