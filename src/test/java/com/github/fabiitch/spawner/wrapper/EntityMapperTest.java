package com.github.fabiitch.spawner.wrapper;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.wrappers.Knight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityMapperTest extends BaseTest {

    @Test
    public void simpleAddRemove() {
        int entityId = world.createEntity();

        Knight knight = knightMapper.get(entityId);
        Assertions.assertEquals(entityId, knight.getId());
        Assertions.assertNull(knight.getAttackBehavior());
        Assertions.assertNull(knight.getSwordComponent());
        Assertions.assertNull(knight.getPosition());



    }
}
