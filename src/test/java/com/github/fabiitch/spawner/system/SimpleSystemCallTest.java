package com.github.fabiitch.spawner.system;

import com.github.fabiitch.spawner.World;
import com.github.fabiitch.spawner.systems.BaseEcsSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test if system update() is called by World.update
 */
public class SimpleSystemCallTest {

    @Test
    public void testActiveInactive() {
        World world = new World();

        BaseSystem systemA = new BaseSystem();
        world.getConfig().addSystem(systemA);

        world.update(10);
        world.update(10);
        Assertions.assertEquals(2, systemA.callCount);

        systemA.setActive(false);
        world.update(10);
        world.update(10);
        Assertions.assertEquals(2, systemA.callCount);

        systemA.setActive(true);
        world.update(10);
        world.update(10);
        Assertions.assertEquals(4, systemA.callCount);
    }

    @Test
    public void testOneSystemInactive() {
        World world = new World();
        BaseSystem systemA = new BaseSystem();
        systemA.setActive(false);
        world.getConfig().addSystem(systemA);

        world.update(10);
        world.update(10);
        Assertions.assertEquals(0, systemA.callCount);
    }
}

class BaseSystem extends BaseEcsSystem {

    public int callCount;

    public BaseSystem() {
        super(50);
    }

    @Override
    public void update(float dt) {
        callCount++;
    }

}
