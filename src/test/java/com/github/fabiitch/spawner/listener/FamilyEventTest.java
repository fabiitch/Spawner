package com.github.fabiitch.spawner.listener;

import com.github.fabiitch.spawner.archetype.ArchetypeBuilder;
import com.github.fabiitch.spawner.archetype.criteria.QueryCriteria;
import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.family.Family;
import com.github.fabiitch.spawner.listeners.FamilyListener;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.data.components.attack.PoisonAuraComponent;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FamilyEventTest extends BaseTest {

    @Test
    public void componentAddRemoveTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .components(QueryCriteria.AllOf, PoisonAuraComponent.class, PositionComponent.class);
        Family family = config.registerFamily(archetypeBuilder);

        FamilyListenerCount listener = new FamilyListenerCount();
        family.addListener(listener);

        int entity1 = world.createEntity();
        int entity2 = world.createEntity();

        positionMapper.addComponent(entity1, new PositionComponent());
        poisonAuraMapper.addComponent(entity2, new PoisonAuraComponent());
        assertEquals(0, listener.getAddCount());

        poisonAuraMapper.addComponent(entity1, new PoisonAuraComponent());
        assertEquals(1, listener.getAddCount());
        assertEquals(entity1, listener.getLastIdAdd());

        positionMapper.addComponent(entity2, new PositionComponent());
        assertEquals(2, listener.getAddCount());
        assertEquals(entity2, listener.getLastIdAdd());

        positionMapper.removeComponent(entity1);
        assertEquals(1, listener.getRemoveCount());
        assertEquals(entity1, listener.getLastIdRemove());
    }

    @Test
    public void entityAddRemoveDestroyTest() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .components(QueryCriteria.AllOf, PoisonAuraComponent.class, PositionComponent.class);
        Family family = config.registerFamily(archetypeBuilder);

        int entity1 = world.createEntity();
        positionMapper.addComponent(entity1, new PositionComponent());
        poisonAuraMapper.addComponent(entity1, new PoisonAuraComponent());

        FamilyListenerCount listener = new FamilyListenerCount();
        family.addListener(listener);

        EntityReference reference = world.removeEntity(entity1);
        assertEquals(1, listener.getRemoveCount());

        world.removeEntity(entity1); //do nothing
        assertEquals(1, listener.getRemoveCount());

        world.addEntity(reference);
        assertEquals(1, listener.getAddCount());

        world.addEntity(reference); //do nothing
        assertEquals(1, listener.getAddCount());

        world.destroyEntity(entity1);
        assertEquals(2, listener.getRemoveCount());

        world.destroyEntity(entity1);//do nothing
        assertEquals(2, listener.getRemoveCount());
    }

    @Test
    public void callOnProtoypeCreate() {
        ArchetypeBuilder archetypeBuilder = ArchetypeBuilder.get()
                .components(QueryCriteria.OneOf, PositionComponent.class);
        Family family = config.registerFamily(archetypeBuilder);
        FamilyListenerCount listener = new FamilyListenerCount();
        family.addListener(listener);

        Prototype prototype = new Prototype();
        prototype.addComponent(new PositionComponent());

        int entityId = world.createEntity(prototype);
        assertEquals(1, listener.getAddCount());
        assertEquals(entityId, listener.getLastIdAdd());
    }

    @Getter
    private static class FamilyListenerCount implements FamilyListener {
        private int addCount, removeCount;
        private int lastIdAdd = -1, lastIdRemove = -1;

        @Override
        public void onEntityAdd(int entityId) {
            addCount++;
            lastIdAdd = entityId;
        }

        @Override
        public void onEntityRemove(int entityId) {
            removeCount++;
            lastIdRemove = entityId;
        }
    }
}
