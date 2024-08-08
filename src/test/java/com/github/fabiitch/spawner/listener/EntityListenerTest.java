package com.github.fabiitch.spawner.listener;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;
import com.github.fabiitch.spawner.data.components.attack.KnifeComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.listeners.EntityListener;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityListenerTest extends BaseTest {

    @Test
    public void entityAddRemoveDestroyTest() {
        EntityListenerCount listener = new EntityListenerCount();

        int entityId = world.createEntity();
        world.getConfig().addEntityListener(entityId, listener);


        EntityReference reference = world.removeEntity(entityId);
        assertEquals(1, listener.getCountEntityRemove());

        world.removeEntity(entityId);//do nothing
        assertEquals(1, listener.getCountEntityRemove());

        world.addEntity(reference);
        assertEquals(1, listener.getCountEntityAdd());

        world.addEntity(reference); //do nothing
        assertEquals(1, listener.getCountEntityAdd());

        world.destroyEntity(entityId);
        assertEquals(1, listener.getCountEntityDestroy());

        world.destroyEntity(entityId); //do nothing
        assertEquals(1, listener.getCountEntityDestroy());
    }

    @Test
    public void componentAddRemoveTest() {
        EntityListenerCount listener = new EntityListenerCount();
        int entityId = world.createEntity();
        world.getConfig().addEntityListener(entityId, listener);

        swordMapper.addComponent(entityId, new SwordComponent(10));
        assertEquals(SwordComponent.class, listener.getLastComponentAdd().getClass());

        swordMapper.removeComponent(entityId);
        assertEquals(SwordComponent.class, listener.getLastComponentRemove().getClass());

        KnifeComponent knifeComponent1 = new KnifeComponent();
        knifeMapper.addComponent(entityId, knifeComponent1);
        assertEquals(knifeComponent1, listener.getLastComponentAdd());

        KnifeComponent knifeComponent2 = new KnifeComponent();
        knifeMapper.addComponent(entityId, knifeComponent2);
        assertEquals(knifeComponent2, listener.getLastComponentAdd());

        knifeMapper.removeComponent(entityId);
        assertEquals(knifeComponent2, listener.getLastComponentRemove());

        //test remove double call
        swordMapper.addComponent(entityId, new SwordComponent(10));
        swordMapper.removeComponent(entityId);
        knifeMapper.removeComponent(entityId);  //already removed
        assertEquals(SwordComponent.class, listener.getLastComponentRemove().getClass());
    }

    @Test
    public void behaviorGetLooseTest() {
        EntityListenerCount listener = new EntityListenerCount();
        int entityId = world.createEntity();
        world.getConfig().addEntityListener(entityId, listener);

        knifeMapper.addComponent(entityId, new KnifeComponent());
        assertInstanceOf(AttackBehavior.class, listener.getLastBehaviorGet());
        assertEquals(KnifeComponent.class, listener.getLastBehaviorGet().getClass());

        swordMapper.addComponent(entityId, new SwordComponent(10));
        assertEquals(KnifeComponent.class, listener.getLastBehaviorGet().getClass()); //stay knife

        knifeMapper.removeComponent(entityId);
        assertNull(listener.getLastBehaviorLoose());

        swordMapper.removeComponent(entityId);
        assertInstanceOf(AttackBehavior.class, listener.getLastBehaviorLoose());
        assertEquals(SwordComponent.class, listener.getLastBehaviorLoose().getClass());

        knifeMapper.removeComponent(entityId);
        assertEquals(SwordComponent.class, listener.getLastBehaviorLoose().getClass());
    }

    @Test
    public void behaviorComponentAddRemoveTest() {
        EntityListenerCount listener = new EntityListenerCount();
        int entityId = world.createEntity();
        world.getConfig().addEntityListener(entityId, listener);

        knifeMapper.addComponent(entityId, new KnifeComponent());
        assertInstanceOf(AttackBehavior.class, listener.getLastComponentAdd());
        assertEquals(KnifeComponent.class, listener.getLastComponentAdd().getClass());

        swordMapper.addComponent(entityId, new SwordComponent(22));
        assertInstanceOf(AttackBehavior.class, listener.getLastComponentAdd());
        assertEquals(SwordComponent.class, listener.getLastComponentAdd().getClass());

        swordMapper.removeComponent(entityId);
        assertInstanceOf(AttackBehavior.class, listener.getLastComponentRemove());
        assertEquals(SwordComponent.class, listener.getLastComponentRemove().getClass());

        knifeMapper.removeComponent(entityId);
        assertInstanceOf(AttackBehavior.class, listener.getLastComponentRemove());
        assertEquals(KnifeComponent.class, listener.getLastComponentRemove().getClass());
    }


    @Test
    public void flagAddRemoveTest() {
        int entityId = world.createEntity();

        EntityListenerCount listener = new EntityListenerCount();
        world.getConfig().addEntityListener(entityId, listener);

        deathFlagMapper.setFlag(entityId);
        assertEquals(deathFlagMapper.getIndex(), listener.lastFlagAdd);

        deathFlagMapper.removeFlag(entityId);
        assertEquals(deathFlagMapper.getIndex(), listener.lastFlagRemove);

        deathFlagMapper.setFlag(entityId);
        outFlagMapper.setFlag(entityId);
        assertEquals(outFlagMapper.getIndex(), listener.lastFlagAdd);

        //re add dont trig
        deathFlagMapper.setFlag(entityId);
        assertEquals(outFlagMapper.getIndex(), listener.lastFlagAdd);

        deathFlagMapper.removeFlag(entityId);
        outFlagMapper.removeFlag(entityId);
        assertEquals(outFlagMapper.getIndex(), listener.lastFlagRemove);

        //re remove dont trig
        deathFlagMapper.removeFlag(entityId);
        assertEquals(outFlagMapper.getIndex(), listener.lastFlagRemove);
    }
}

@Getter
class EntityListenerCount implements EntityListener {

    int countEntityAdd, countEntityRemove, countEntityDestroy;
    Object lastComponentAdd, lastComponentRemove;
    Object lastBehaviorGet, lastBehaviorLoose;
    Object lastBehaviorAdd, lastBehaviorRemove;
    int lastFlagAdd = -1;
    int lastFlagRemove = -1;

    @Override
    public void onAdd() {
        countEntityAdd++;
    }

    @Override
    public void onRemove() {
        countEntityRemove++;
    }

    @Override
    public void onDestroy() {
        countEntityDestroy++;
    }

    @Override
    public void onFlagAdd(int flagIndex) {
        lastFlagAdd = flagIndex;
    }

    @Override
    public void onFlagRemove(int flagIndex) {
        lastFlagRemove = flagIndex;
    }

    @Override
    public void onComponentAdd(int componentIndex, Object component) {
        lastComponentAdd = component;
    }

    @Override
    public void onComponentRemove(int componentIndex, Object component) {
        lastComponentRemove = component;
    }

    @Override
    public void onBehaviorComponentAdd(int indexBehavior, Object componentBehavior) {
        lastBehaviorAdd = componentBehavior;
    }

    @Override
    public void onBehaviorComponentRemove(int indexBehavior, Object componentBehavior) {
        lastBehaviorRemove = componentBehavior;
    }

    @Override
    public void onBehaviorGet(int componentIndex, Object behavior) {
        lastBehaviorGet = behavior;
    }

    @Override
    public void onBehaviorLoose(int componentIndex, Object behavior) {
        lastBehaviorLoose = behavior;
    }
}
