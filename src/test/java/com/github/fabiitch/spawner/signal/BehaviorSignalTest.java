package com.github.fabiitch.spawner.signal;

import com.badlogic.gdx.math.Vector2;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.signals.AiPathFinderBehavior;
import com.github.fabiitch.spawner.data.signals.SimplePathFinder;
import com.github.fabiitch.spawner.signals.SignalListener;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BehaviorSignalTest extends BaseTest {
    @Test
    public void testNotify() {
        BehaviorSignalTest.AiPathSignal listener = new BehaviorSignalTest.AiPathSignal();
        config.addSignalListener(listener, pathFinderMapper);

        int entityA = world.createEntity();
        simplePathFinderMapper.addComponent(entityA, new SimplePathFinder(100, 100));

        Assertions.assertNotEquals(listener.lastEntityId, entityA);

        SimplePathFinder pathEntityA = simplePathFinderMapper.getComponent(entityA);

        pathEntityA.setPosition(50, 50);
        Assertions.assertNotEquals(listener.lastEntityId, entityA);
        pathEntityA.setPosition(100, 100);

        Assertions.assertEquals(listener.lastEntityId, entityA);
        Assertions.assertEquals(listener.lastTarget, new Vector2(100, 100));
    }


    @Getter
    static class AiPathSignal implements SignalListener<AiPathFinderBehavior> {
        int lastEntityId = -1000;
        Vector2 lastTarget;

        @Override
        public void onUpdate(int entityId, AiPathFinderBehavior aiPath) {
            lastEntityId = entityId;
            lastTarget = aiPath.getTarget();
        }
    }
}
