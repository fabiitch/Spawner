package com.github.fabiitch.spawner.signal;

import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.signals.StateMachineSignal;
import com.github.fabiitch.spawner.signals.SignalListener;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComponentSignalTest extends BaseTest {

    @Test
    public void testNotify() {
        StateMachineSignalListener listener = new StateMachineSignalListener();
        config.addSignalListener(listener, stateMachineSignalMapper);

        int entityA = world.createEntity();
        stateMachineSignalMapper.addComponent(entityA, new StateMachineSignal());

        int entityB = world.createEntity();
        stateMachineSignalMapper.addComponent(entityB, new StateMachineSignal());


        StateMachineSignal stateA = stateMachineSignalMapper.getComponent(entityA);
        stateA.setState(StateMachineSignal.State.Attack);
        Assertions.assertEquals(entityA, listener.lastEntityId);
        Assertions.assertEquals(StateMachineSignal.State.Attack, listener.lastState);

        StateMachineSignal stateB = stateMachineSignalMapper.getComponent(entityA);
        stateB.setState(StateMachineSignal.State.Idle); //no change
        Assertions.assertEquals(entityA, listener.lastEntityId);
    }

    @Getter
    static class StateMachineSignalListener implements SignalListener<StateMachineSignal> {
        int lastEntityId = -1000;
        StateMachineSignal.State lastState;

        @Override
        public void onUpdate(int entityId, StateMachineSignal signalData) {
            lastEntityId = entityId;
            lastState = signalData.getState();
        }
    }
}
