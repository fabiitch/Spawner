package com.github.fabiitch.spawner.listener;

import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.listeners.flag.FlagListener;
import com.github.fabiitch.spawner.BaseTest;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlagListenerTest extends BaseTest {

    @Test
    public void addRemoveComponentTest() {
        FlagListenerCounter listener = new FlagListenerCounter();
        deathFlagMapper.addListener(listener);

        int entity1 = world.createEntity();

        deathFlagMapper.setFlag(entity1);
        assertEquals(1, listener.callAdd);

        //readd dont trig
        deathFlagMapper.setFlag(entity1);
        assertEquals(1, listener.callAdd);


        deathFlagMapper.removeFlag(entity1);
        assertEquals(1, listener.callRemove);

        //re remove dont trig
        deathFlagMapper.removeFlag(entity1);
        assertEquals(1, listener.callRemove);
    }

    @Test
    public void noCallWhenEntityRemovedAddedOrDestroy() {
        int entity1 = world.createEntity();
        deathFlagMapper.setFlag(entity1);

        //add listener
        FlagListenerCounter listener = new FlagListenerCounter();
        deathFlagMapper.addListener(listener);

        EntityReference reference = world.removeEntity(entity1);
        assertEquals(0, listener.callRemove);

        world.addEntity(reference);
        assertEquals(0, listener.callAdd);

        world.destroyEntity(entity1);
        assertEquals(0, listener.callRemove);
    }

    @Test
    public void callOnPrototypeCreateTest() {
        Prototype prototype = new Prototype();
        prototype.addFlag(deathFlagMapper);

        FlagListenerCounter listener = new FlagListenerCounter();
        deathFlagMapper.addListener(listener);

        world.createEntity(prototype);
        assertEquals(1, listener.getCallAdd());
    }

    @Getter
    private static class FlagListenerCounter<T> implements FlagListener {
        private int callAdd, callRemove;

        @Override
        public void onFlagAdd(int entityId, int flagIndex) {
            callAdd++;
        }

        @Override
        public void onFlagRemove(int entityId, int flagIndex) {
            callRemove++;
        }
    }
}
