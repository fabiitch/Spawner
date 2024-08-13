package com.github.fabiitch.spawner.flags;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.flag.FlagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlagMapperTest extends BaseTest {

    @Test
    public void addRemoveTest() {
        int entityId = world.createEntity();

        assertFalse(deathFlagMapper.hasFlag(entityId));
        assertFalse(outFlagMapper.hasFlag(entityId));

        deathFlagMapper.setFlag(entityId);
        assertTrue(deathFlagMapper.hasFlag(entityId));

        deathFlagMapper.removeFlag(entityId);
        assertFalse(deathFlagMapper.hasFlag(entityId));

        outFlagMapper.setFlag(entityId);
        assertTrue(outFlagMapper.hasFlag(entityId));

        outFlagMapper.removeFlag(entityId);
        assertFalse(outFlagMapper.hasFlag(entityId));
    }

    @Test
    public void testRemoveWhenEntityRemove() {
        int entityIdA = world.createEntity();
        int entityIdB = world.createEntity();

        deathFlagMapper.setFlag(entityIdA);
        deathFlagMapper.setFlag(entityIdB);

        EntityReference referenceA = world.removeEntity(entityIdA);
        assertFalse(deathFlagMapper.hasFlag(entityIdA));
        world.addEntity(referenceA);
        assertTrue(deathFlagMapper.hasFlag(entityIdA));

        world.destroyEntity(entityIdB);
        assertFalse(deathFlagMapper.hasFlag(entityIdB));
    }

    @Test
    public void getAllTest() {
        IntArray all;

        int entityIdA = world.createEntity();
        int entityIdB = world.createEntity();
        int entityIdC = world.createEntity();

        all = deathFlagMapper.getAll(new IntArray());
        Assertions.assertEquals(0, all.size);

        deathFlagMapper.setFlag(entityIdA);
        deathFlagMapper.setFlag(entityIdC);

        all = deathFlagMapper.getAll(new IntArray());
        Assertions.assertEquals(2, all.size);
        Assertions.assertEquals(entityIdA, all.get(0));
        Assertions.assertEquals(entityIdC, all.get(1));

        deathFlagMapper.removeFlag(entityIdA);
        deathFlagMapper.setFlag(entityIdB);

        all = deathFlagMapper.getAll(new IntArray());
        Assertions.assertEquals(2, all.size);
        Assertions.assertEquals(entityIdB, all.get(0));
        Assertions.assertEquals(entityIdC, all.get(1));
    }

    public enum Flags {invincible, invisible,}

    @Test
    public void testFlagWithEnum() {
        config.registerFlags(Flags.values());

        int entityA = world.createEntity();

        FlagMapper flagMapper = world.getFlagMapper(Flags.invincible);

        Assertions.assertFalse(flagMapper.hasFlag(entityA));

        flagMapper.setFlag(entityA);
        Assertions.assertTrue(flagMapper.hasFlag(entityA));
    }
}
