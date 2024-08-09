package com.github.fabiitch.spawner.system;

import com.github.fabiitch.spawner.World;
import com.github.fabiitch.spawner.WorldConfig;
import com.github.fabiitch.spawner.systems.EcsSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemOrderLoopTest {
    World world;
    WorldConfig config;
    SystemMock systemA, systemB, systemC;

    @BeforeEach
    public void setUp() {
        world = new World();
        config = world.getConfig();
        systemA = new SystemMock(100);
        systemB = new SystemMock(200);
        systemC = new SystemMock(300);
        config.addSystem(systemA, systemB, systemC);
    }

    @Test
    public void testNormal() {
        for (int i = 1; i < 100; i++) {
            world.update(1);
            checkCountForAll(i);
        }
    }

    @Test
    public void testRemoveLowerPriority() {
        SystemRemover systemRemover = new SystemRemover(150, systemA);
        config.addSystem(systemRemover);

        world.update(1);
        world.update(1);
        checkCountCall(2, systemRemover, systemB, systemC);
        checkCountCall(1, systemA);
    }

    @Test
    public void testRemoveHighterPriority() {
        SystemRemover systemRemover = new SystemRemover(150, systemB);
        config.addSystem(systemRemover);

        world.update(1);
        world.update(1);
        checkCountCall(2, systemA, systemRemover, systemC);
        checkCountCall(0, systemB);
    }

    @Test
    public void testAddLowerPriority() {
        SystemMock systemToAdd = new SystemMock(1);
        SystemAdder systemAdder = new SystemAdder(150, systemToAdd);
        config.addSystem(systemAdder);

        world.update(1);
        world.update(1);
        checkCountCall(2, systemA, systemAdder, systemB, systemC);
        checkCountCall(1, systemToAdd);
    }

    @Test
    public void testAddHighterPriority() {
        SystemMock systemToAdd = new SystemMock(250);
        SystemAdder systemAdder = new SystemAdder(150, systemToAdd);
        config.addSystem(systemAdder);

        world.update(1);
        world.update(1);
        checkCountCall(2, systemA, systemAdder, systemToAdd, systemB, systemC);
    }

    private void checkCountForAll(int expected) {
        checkCountCall(expected, systemA, systemB, systemC);
    }

    private void checkCountCall(int expected, SystemMock... systems) {
        for (SystemMock systemMock : systems)
            Assertions.assertEquals(expected, systemMock.callCount);
    }

    private static class SystemMock implements EcsSystem {
        public String name;
        public int priority;
        public int callCount;

        public SystemMock(int priority) {
            this.priority = priority;
            this.name = "System:" + priority;
        }

        @Override
        public int getPriority() {
            return priority;
        }

        @Override
        public void update(float dt) {
            callCount++;
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public void setActive(boolean active) {

        }

    }

    private abstract class SystemAddRemove extends SystemMock {
        SystemMock systemToModify;
        boolean done = false;
        boolean add;

        public SystemAddRemove(int priority, SystemMock systemToModify, boolean add) {
            super(priority);
            this.systemToModify = systemToModify;
            this.add = add;
        }

        @Override
        public void update(float dt) {
            super.update(dt);
            if (!done) {
                if (add)
                    config.addSystem(systemToModify);
                else
                    config.removeSystem(systemToModify);
                done = true;
            }
        }
    }

    private class SystemRemover extends SystemAddRemove {
        public SystemRemover(int priority, SystemMock systemToModify) {
            super(priority, systemToModify, false);
        }
    }

    private class SystemAdder extends SystemAddRemove {
        public SystemAdder(int priority, SystemMock systemToModify) {
            super(priority, systemToModify, true);
        }
    }
}
