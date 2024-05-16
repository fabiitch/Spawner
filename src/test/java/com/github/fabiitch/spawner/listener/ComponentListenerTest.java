package com.github.fabiitch.spawner.listener;

import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.listeners.ComponentListener;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComponentListenerTest extends BaseTest {

    @Test
    public void addRemoveComponentTest() {
        ComponentListenerCounter<SwordComponent> swordListener = new ComponentListenerCounter<>();
        swordMapper.addListener(swordListener);

        int entity1 = world.createEntity();
        int entity2 = world.createEntity();

        swordMapper.addComponent(entity1, new SwordComponent(10));
        assertEquals(1, swordListener.callAdd);

        swordMapper.addComponent(entity2, new SwordComponent(10));
        assertEquals(2, swordListener.callAdd);

        swordMapper.addComponent(entity1, new SwordComponent(10)); //add count even entity has already component
        assertEquals(3, swordListener.callAdd);

        swordMapper.removeComponent(entity1);
        assertEquals(1, swordListener.callRemove);

        swordMapper.removeComponent(entity2);
        assertEquals(2, swordListener.callRemove);
    }

    @Test
    public void noCallWhenEntityRemovedAddedOrDestroy() {
        ComponentListenerCounter<SwordComponent> swordListener = new ComponentListenerCounter<>();
        swordMapper.addListener(swordListener);

        int entity1 = world.createEntity();
        swordMapper.addComponent(entity1, new SwordComponent(50));
        assertEquals(1, swordListener.callAdd);

        EntityReference entityReference = world.removeEntity(entity1);
        assertEquals(1, swordListener.callAdd);
        assertEquals(0, swordListener.callRemove);

        world.addEntity(entityReference);
        assertEquals(1, swordListener.callAdd);
        assertEquals(0, swordListener.callRemove);

        swordMapper.removeComponent(entity1);
        assertEquals(1, swordListener.callAdd);
        assertEquals(1, swordListener.callRemove);

        world.destroyEntity(entity1);
        assertEquals(1, swordListener.callAdd);
        assertEquals(1, swordListener.callRemove);
    }

    @Test
    public void callOnPrototypeCreateTest() {
        ComponentListenerCounter listener = new ComponentListenerCounter();
        swordMapper.addListener(listener);

        Prototype prototype = new Prototype();
        prototype.addComponent(new SwordComponent(50));

        world.createEntity(prototype);
        assertEquals(1, listener.getCallAdd());
    }


    @Getter
    private static class ComponentListenerCounter<T> implements ComponentListener<T> {
        private int callAdd, callRemove;

        @Override
        public void onComponentAdd(int entityId, T component, int componentIndex) {
            callAdd++;
        }

        @Override
        public void onComponentRemove(int entityId, T component, int componentIndex) {
            callRemove++;
        }
    }
}
