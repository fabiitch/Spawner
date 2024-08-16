package com.github.fabiitch.spawner.signals;

import com.badlogic.gdx.utils.IntMap;
import com.github.fabiitch.spawner.behavior.BehaviorManager;
import com.github.fabiitch.spawner.behavior.BehaviorMapper;
import com.github.fabiitch.spawner.component.ComponentManager;
import com.github.fabiitch.spawner.component.ComponentMapper;

public class SignalDataManager {

    private final ComponentManager componentManager;
    private final BehaviorManager behaviorManager;

    private final IntMap<ComponentSignalMultiplexer> componentListenerMap = new IntMap<>();
    private final IntMap<BehaviorSignalMultiplexer> behaviorListenerMap = new IntMap<>();

    public SignalDataManager(ComponentManager componentManager, BehaviorManager behaviorManager) {
        this.componentManager = componentManager;
        this.behaviorManager = behaviorManager;
    }


    public <C extends SignalData> void addListener(SignalListener<C> listener, ComponentMapper<C> mapper) {
        ComponentSignalMultiplexer<C> signalMultiplexer = componentListenerMap.get(mapper.getIndex());
        if (signalMultiplexer == null) {
            signalMultiplexer = new ComponentSignalMultiplexer<>();
            componentListenerMap.put(mapper.getIndex(), signalMultiplexer);
        }
        signalMultiplexer.addListener(listener);

        componentManager.addInternalListener(mapper.getIndex(), signalMultiplexer);
    }

    public <C extends SignalData> void removeListener(SignalListener<C> listener, ComponentMapper<C> mapper) {
        ComponentSignalMultiplexer<C> signalMultiplexer = componentListenerMap.get(mapper.getIndex());
        if (signalMultiplexer != null) {
            signalMultiplexer.removeListener(listener);
        }
        //TODO remove internal listener maybe
    }

    public <C extends SignalData> void addListener(SignalListener<C> listener, BehaviorMapper<C> mapper) {
        BehaviorSignalMultiplexer<C> signalMultiplexer = behaviorListenerMap.get(mapper.getIndex());
        if (signalMultiplexer == null) {
            signalMultiplexer = new BehaviorSignalMultiplexer<>();
            behaviorListenerMap.put(mapper.getIndex(), signalMultiplexer);
        }
        signalMultiplexer.addListener(listener);

        behaviorManager.addInternalListener(mapper.getIndex(), signalMultiplexer);
    }

    public <C extends SignalData> void removeListener(SignalListener<C> listener, BehaviorMapper<C> mapper) {
        BehaviorSignalMultiplexer<C> signalMultiplexer = behaviorListenerMap.get(mapper.getIndex());
        if (signalMultiplexer != null) {
            signalMultiplexer.removeListener(listener);
        }
        //TODO remove internal listener maybe
    }
}
