package com.github.fabiitch.spawner.system;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.sort.EntityComparator;
import com.github.fabiitch.spawner.systems.EntitySystem;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntitySystemOrderedTest extends BaseTest {


    @Test
    public void testEntitiesOrdered() {
        AttackSystemOrdered systemOrdered = new AttackSystemOrdered();
        config.addSystem(systemOrdered);

        int entityA = world.createEntity();
        int entityB = world.createEntity();
        int entityC = world.createEntity();
        int entityD = world.createEntity();

        swordMapper.addComponent(entityA, new SwordComponent(10));
        swordMapper.addComponent(entityB, new SwordComponent(30));
        swordMapper.addComponent(entityC, new SwordComponent(50));
        swordMapper.addComponent(entityD, new SwordComponent(20));

        world.update(1);
        Assertions.assertEquals(entityA ,systemOrdered.getOrderEntities().get(0));
        Assertions.assertEquals(entityD ,systemOrdered.getOrderEntities().get(1));
        Assertions.assertEquals(entityB ,systemOrdered.getOrderEntities().get(2));
        Assertions.assertEquals(entityC ,systemOrdered.getOrderEntities().get(3));

        swordMapper.getComponent(entityA).setDamage(10000);
        swordMapper.getComponent(entityC).setDamage(20000);

        world.update(1);
        Assertions.assertEquals(entityD ,systemOrdered.getOrderEntities().get(0));
        Assertions.assertEquals(entityB ,systemOrdered.getOrderEntities().get(1));
        Assertions.assertEquals(entityA ,systemOrdered.getOrderEntities().get(2));
        Assertions.assertEquals(entityC ,systemOrdered.getOrderEntities().get(3));
    }


    private class AttackSystemOrdered extends EntitySystem {
        @Getter
        IntArray orderEntities = new IntArray();

        EntityComparator comparator = (o1, o2) -> {
            AttackBehavior attackEntity1 = attackBehaviorMapper.getBehaviors(o1).getFirst();
            AttackBehavior attackEntity2 = attackBehaviorMapper.getBehaviors(o2).getFirst();
            return Integer.compare(attackEntity1.attack(), attackEntity2.attack());
        };

        public AttackSystemOrdered() {
            super(attackArchetype, 0);
            setComparator(comparator);
        }

        @Override
        public void update(float dt) {
            orderEntities.clear();
            super.update(dt);
        }

        @Override
        protected void processEntity(int entityId, float dt) {
            orderEntities.add(entityId);
        }
    }
}
