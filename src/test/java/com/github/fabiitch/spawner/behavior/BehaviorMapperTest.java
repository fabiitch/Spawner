package com.github.fabiitch.spawner.behavior;

import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.data.components.attack.KnifeComponent;
import com.github.fabiitch.spawner.data.components.attack.PoisonAuraComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.utils.collections.SafeTab;
import com.github.fabiitch.spawner.utils.collections.Tab;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BehaviorMapperTest extends BaseTest {

    @Test
    public void interfaceTest() {
        int entityId = world.createEntity();

        //get
        SafeTab<AttackBehavior> behaviors = attackBehaviorMapper.getBehaviors(entityId);
        assertNull(behaviors);
        assertFalse(attackBehaviorMapper.hasBehavior(entityId));

        //add
        knifeMapper.addComponent(entityId, new KnifeComponent());
        behaviors = attackBehaviorMapper.getBehaviors(entityId);
        assertEquals(1, behaviors.size());
        assertTrue(attackBehaviorMapper.hasBehavior(entityId));

        //add other
        poisonAuraMapper.addComponent(entityId, new PoisonAuraComponent());
        behaviors = attackBehaviorMapper.getBehaviors(entityId);
        assertEquals(2, behaviors.size());
        assertTrue(attackBehaviorMapper.hasBehavior(entityId));

        //delete one
        knifeMapper.removeComponent(entityId);
        behaviors = attackBehaviorMapper.getBehaviors(entityId);
        assertEquals(1, behaviors.size());
        assertTrue(attackBehaviorMapper.hasBehavior(entityId));

        //delete second
        poisonAuraMapper.removeComponent(entityId);
        behaviors = attackBehaviorMapper.getBehaviors(entityId);
        assertEquals(0, behaviors.size());
        assertFalse(attackBehaviorMapper.hasBehavior(entityId));


        //re add
        poisonAuraMapper.addComponent(entityId, new PoisonAuraComponent());
        behaviors = attackBehaviorMapper.getBehaviors(entityId);
        assertEquals(1, behaviors.size());
        assertTrue(attackBehaviorMapper.hasBehavior(entityId));
    }


    @Test
    public void removeBehaviorTest() {
        Prototype prototype = new Prototype();

        prototype.addComponent(new PositionComponent());
        prototype.addComponent(new KnifeComponent());
        prototype.addComponent(new SwordComponent(10));

        int entityId = world.createEntity(prototype);
        assertTrue(attackBehaviorMapper.hasBehavior(entityId));

        attackBehaviorMapper.removeBehavior(entityId);
        assertFalse(attackBehaviorMapper.hasBehavior(entityId));
        assertFalse(swordMapper.hasComponent(entityId));
        assertFalse(knifeMapper.hasComponent(entityId));
        assertTrue(positionMapper.hasComponent(entityId));
    }

    @Test
    public void getAllTest() {
        int entityIdA = world.createEntity();
        int entityIdB = world.createEntity();
        int entityIdC = world.createEntity();
        int entityIdD = world.createEntity();

        swordMapper.addComponent(entityIdA, new SwordComponent(10));
        knifeMapper.addComponent(entityIdA, new KnifeComponent());

        knifeMapper.addComponent(entityIdC, new KnifeComponent());

        knifeMapper.addComponent(entityIdD, new KnifeComponent());
        knifeMapper.removeComponent(entityIdD);


        SafeTab<Tab<AttackBehavior>> all = attackBehaviorMapper.getAll();
        assertEquals(2, all.get(entityIdA).size());
        assertEquals(1, all.get(entityIdC).size());
        Assertions.assertNull(all.get(entityIdB));
        assertEquals(0, all.get(entityIdD).size());
    }
}
