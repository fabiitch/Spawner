package com.github.fabiitch.spawner.wrapper;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.data.wrappers.Knight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityMapperTest extends BaseTest {

    @Test
    public void addRemoveComponent() {
        int entityId = world.createEntity();

        Knight knight = knightMapper.get(entityId);
        Assertions.assertEquals(entityId, knight.getEntityId());
        Assertions.assertNull(knight.getAttackBehavior());
        Assertions.assertNull(knight.getSwordComponent());
        Assertions.assertNull(knight.getPosition());


        swordMapper.addComponent(knight.getEntityId(), new SwordComponent(10));
        Assertions.assertEquals(10, knight.getSwordComponent().attack());
        Assertions.assertEquals(10, knight.getAttackBehavior().attack());

        swordMapper.removeComponent(knight.getEntityId());
        Assertions.assertNull(knight.getAttackBehavior());
        Assertions.assertNull(knight.getSwordComponent());


        //flag
        Assertions.assertFalse(knight.isDead());

        deathFlagMapper.setFlag(knight.getEntityId());
        Assertions.assertTrue(knight.isDead());

        deathFlagMapper.removeFlag(knight.getEntityId());
        Assertions.assertFalse(knight.isDead());
    }
}
