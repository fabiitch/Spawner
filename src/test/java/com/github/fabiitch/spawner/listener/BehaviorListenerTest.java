package com.github.fabiitch.spawner.listener;

import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.listeners.BehaviorListener;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.attack.KnifeComponent;
import com.github.fabiitch.spawner.data.components.attack.PoisonAuraComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BehaviorListenerTest extends BaseTest {

    @Test
    public void addRemoveComponentTest() {
        BehaviorListenerCounter listener = new BehaviorListenerCounter();
        attackBehaviorMapper.addListener(listener);

        int entityA = world.createEntity();
        int entityB = world.createEntity();

        {
            //add sword to A
            swordMapper.addComponent(entityA, new SwordComponent(50));

            assertEquals(entityA, listener.getLastEntityIdGet());
            assertEquals(SwordComponent.class, listener.getClassCauseBehaviorGet());
            assertEquals(1, listener.getComponentAddCount());
        }
        {
            //recall if add sword to A again
            swordMapper.addComponent(entityA, new SwordComponent(50));

            assertEquals(entityA, listener.getLastEntityIdGet());
            assertEquals(SwordComponent.class, listener.getClassCauseBehaviorGet());
            assertEquals(2, listener.getComponentAddCount());
        }
        {
            //add poison to B
            poisonAuraMapper.addComponent(entityB, new PoisonAuraComponent());

            assertEquals(entityB, listener.getLastEntityIdGet());
            assertEquals(PoisonAuraComponent.class, listener.getClassCauseBehaviorGet());
            assertEquals(3, listener.getComponentAddCount());
        }
        {
            //add poison to A
            poisonAuraMapper.addComponent(entityA, new PoisonAuraComponent());

            assertEquals(entityA, listener.getLastEntityIdGet());
            assertEquals(PoisonAuraComponent.class, listener.getClassCauseBehaviorGet());
            assertEquals(4, listener.getComponentAddCount());
        }

        {   //remove sword to A
            swordMapper.removeComponent(entityA);
            assertEquals(-1, listener.getLastEntityIdLoose());
            assertEquals(1, listener.getComponentRemoveCount());
        }

        {
            //remove poison to B
            poisonAuraMapper.removeComponent(entityB);
            assertEquals(entityB, listener.getLastEntityIdLoose());
            assertEquals(PoisonAuraComponent.class, listener.getClassCauseBehaviorLoose());
            assertEquals(2, listener.getComponentRemoveCount());
        }
        {
            //remove poison to A again
            poisonAuraMapper.removeComponent(entityB);
            assertEquals(entityB, listener.getLastEntityIdLoose());
            assertEquals(PoisonAuraComponent.class, listener.getClassCauseBehaviorLoose());
            assertEquals(2, listener.getComponentRemoveCount());
        }
    }

    @Test
    public void callWhenBehaviorRemove() {
        BehaviorListenerCounter listener = new BehaviorListenerCounter();
        attackBehaviorMapper.addListener(listener);

        int entityId = world.createEntity();

        swordMapper.addComponent(entityId, new SwordComponent(50));
        poisonAuraMapper.addComponent(entityId, new PoisonAuraComponent());
        assertEquals(SwordComponent.class, listener.getClassCauseBehaviorGet());
        assertEquals(entityId, listener.getLastEntityIdGet());

        attackBehaviorMapper.removeBehavior(entityId);

        assertEquals(entityId, listener.getLastEntityIdLoose());
        assertEquals(PoisonAuraComponent.class, listener.getClassCauseBehaviorLoose());
        assertEquals(2, listener.getComponentRemoveCount());
    }

    @Test
    public void noCallWhenEntityRemovedAddedOrDestroy() {
        int entity = world.createEntity();
        swordMapper.addComponent(entity, new SwordComponent(50));

        BehaviorListenerCounter listener = new BehaviorListenerCounter();
        attackBehaviorMapper.addListener(listener);

        assertEquals(-1, listener.getLastEntityIdGet());
        assertEquals(null, listener.getClassCauseBehaviorGet());
        assertEquals(0, listener.getComponentAddCount());

        //no call on remove / add / destroy entity
        EntityReference entityReference = world.removeEntity(entity);
        assertEquals(-1, listener.getLastEntityIdLoose());
        assertEquals(null, listener.getClassCauseBehaviorLoose());
        assertEquals(0, listener.getComponentRemoveCount());

        world.addEntity(entityReference);
        assertEquals(-1, listener.getLastEntityIdGet());
        assertEquals(null, listener.getClassCauseBehaviorGet());
        assertEquals(0, listener.getComponentAddCount());

        world.destroyEntity(entity);
        assertEquals(-1, listener.getLastEntityIdLoose());
        assertEquals(null, listener.getClassCauseBehaviorLoose());
        assertEquals(0, listener.getComponentRemoveCount());
    }

    @Test
    public void callOnProtoypeCreate() {
        BehaviorListenerCounter listener = new BehaviorListenerCounter();
        attackBehaviorMapper.addListener(listener);

        Prototype prototype = new Prototype();
        prototype.addComponent(new SwordComponent(50));

        int entityId = world.createEntity(prototype);
        assertEquals(entityId, listener.getLastEntityIdGet());
        assertEquals(SwordComponent.class, listener.getClassCauseBehaviorGet());
        assertEquals(1, listener.getComponentAddCount());
    }

    @Test
    public void componentIndexCallTest() {
        BehaviorListenerCounter listener = new BehaviorListenerCounter();
        attackBehaviorMapper.addListener(listener);

        int entityId = world.createEntity();

        swordMapper.addComponent(entityId, new SwordComponent(1));
        assertEquals(swordMapper.getIndex(), listener.getIndexComponentAdd());

        knifeMapper.addComponent(entityId, new KnifeComponent());
        assertEquals(knifeMapper.getIndex(), listener.getIndexComponentAdd());

        swordMapper.removeComponent(entityId);
        assertEquals(swordMapper.getIndex(), listener.getIndexComponentRemove());

        knifeMapper.removeComponent(entityId);
        assertEquals(knifeMapper.getIndex(), listener.getIndexComponentRemove());
    }

    @Getter
    private static class BehaviorListenerCounter implements BehaviorListener {

        private int lastEntityIdGet = -1, lastEntityIdLoose = -1;
        private Class<?> classCauseBehaviorGet, classCauseBehaviorLoose;

        private int componentAddCount, componentRemoveCount;
        private int indexComponentAdd, indexComponentRemove;

        @Override
        public void onBehaviorGet(int entityId, Object behavior, int behaviorIndex) {
            lastEntityIdGet = entityId;
            classCauseBehaviorGet = behavior.getClass();
        }

        @Override
        public void onBehaviorLoose(int entityId, Object behavior, int behaviorIndex) {
            lastEntityIdLoose = entityId;
            classCauseBehaviorLoose = behavior.getClass();
        }

        @Override
        public void onComponentAdd(int entityId, Object component, int componentIndex) {
            componentAddCount++;
            indexComponentAdd = componentIndex;
        }

        @Override
        public void onComponentRemove(int entityId, Object component, int componentIndex) {
            componentRemoveCount++;
            indexComponentRemove = componentIndex;
        }
    }
}
