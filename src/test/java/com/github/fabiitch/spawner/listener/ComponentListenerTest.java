package com.github.fabiitch.spawner.listener;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.listeners.component.ComponentListener;
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
        assertEquals(entity1, swordListener.entityId);

        swordMapper.addComponent(entity2, new SwordComponent(10));
        assertEquals(2, swordListener.callAdd);
        assertEquals(entity2, swordListener.entityId);

        swordMapper.addComponent(entity1, new SwordComponent(10)); //add count even entity has already component
        assertEquals(3, swordListener.callAdd);
        assertEquals(entity1, swordListener.entityId);

        swordMapper.removeComponent(entity1);
        assertEquals(1, swordListener.callRemove);
        assertEquals(entity1, swordListener.entityId);

        swordMapper.removeComponent(entity2);
        assertEquals(2, swordListener.callRemove);
        assertEquals(entity2, swordListener.entityId);
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

    @Test
    public void callUpdatedTest() {
        ComponentListenerCounter<SwordComponent> swordListener = new ComponentListenerCounter<>();
        swordMapper.addListener(swordListener);

        int entityA = world.createEntity();
        swordMapper.addComponent(entityA, new SwordComponent(50));

        swordMapper.updated(entityA);
        assertEquals(1, swordListener.callUpdate);
        assertEquals(entityA, swordListener.entityId);

        //no component , so no updated
        int entityB = world.createEntity();
        swordMapper.updated(entityB);

        assertEquals(1, swordListener.callUpdate);
        assertEquals(entityA, swordListener.entityId);
    }

    @Getter
    private static class ComponentListenerCounter<T> implements ComponentListener<T> {
        private int callAdd, callRemove, callUpdate;
        private int entityId;

        @Override
        public void onComponentAdd(int entityId, T component, int componentIndex) {
            this.entityId = entityId;
            callAdd++;
        }

        @Override
        public void onComponentRemove(int entityId, T component, int componentIndex) {
            this.entityId = entityId;
            callRemove++;
        }

        @Override
        public void onComponentUpdate(int entityId, T component, int componentIndex) {
            this.entityId = entityId;
            callUpdate++;
        }
    }
}
