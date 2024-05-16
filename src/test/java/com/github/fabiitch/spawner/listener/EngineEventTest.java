package com.github.fabiitch.spawner.listener;

import com.github.fabiitch.spawner.entity.EntityReference;
import com.github.fabiitch.spawner.entity.Prototype;
import com.github.fabiitch.spawner.listeners.WorldListener;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EngineEventTest extends BaseTest {

    @Test
    public void engineListenerTest() {
        EngineWorldListenerCount listener = new EngineWorldListenerCount();
        world.getConfig().addEngineListener(listener);

        int entityA = world.createEntity();
        Assertions.assertEquals(1, listener.getCallAddCreate());
        Assertions.assertEquals(entityA, listener.getLastIdCreated());

        int entityB = world.createEntity();
        Assertions.assertEquals(2, listener.getCallAddCreate());
        Assertions.assertEquals(entityB, listener.getLastIdCreated());

        EntityReference referenceA = world.removeEntity(entityA);
        Assertions.assertEquals(1, listener.getCallRemoveCount());
        Assertions.assertEquals(entityA, listener.getLastIdRemove());

        world.removeEntity(entityA); //do nothing
        Assertions.assertEquals(1, listener.getCallRemoveCount());

        world.addEntity(referenceA);
        Assertions.assertEquals(1, listener.getCallAddCount());
        Assertions.assertEquals(entityA, listener.getLastIdAdd());

        world.addEntity(referenceA); // do nothing
        Assertions.assertEquals(1, listener.getCallAddCount());

        world.removeEntity(entityA);
        Assertions.assertEquals(2, listener.getCallRemoveCount());

        //Do nothing cause removen
        world.destroyEntity(entityA);
        Assertions.assertEquals(0, listener.getCallDestroyCount());
        Assertions.assertEquals(-1, listener.getLastIdDestroy());

        world.addEntity(referenceA);
        world.destroyEntity(entityA);
        Assertions.assertEquals(1, listener.getCallDestroyCount());
        Assertions.assertEquals(entityA, listener.getLastIdDestroy());

        Prototype prototype = new Prototype();
        prototype.addComponent(new PositionComponent());
        int entityC = world.createEntity(prototype);
        Assertions.assertEquals(3, listener.getCallAddCreate());
        Assertions.assertEquals(entityC, listener.getLastIdCreated());
    }


    @Getter
    private class EngineWorldListenerCount implements WorldListener {
        private int callAddCreate, callAddCount, callRemoveCount, callDestroyCount;
        private int lastIdCreated = -1, lastIdAdd = -1, lastIdRemove = -1, lastIdDestroy = -1;

        @Override
        public void onEntityCreate(int entityId) {
            callAddCreate++;
            lastIdCreated = entityId;
        }

        @Override
        public void onEntityAdd(int entityId) {
            callAddCount++;
            lastIdAdd = entityId;
        }

        @Override
        public void onEntityRemove(int entityId) {
            callRemoveCount++;
            lastIdRemove = entityId;
        }

        @Override
        public void onEntityDestroy(int entityId) {
            callDestroyCount++;
            lastIdDestroy = entityId;
        }
    }
}
