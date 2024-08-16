package com.github.fabiitch.spawner.data.signals;

import com.github.fabiitch.spawner.signals.SignalData;
import lombok.Getter;

@Getter
public class StateMachineSignal extends SignalData {

    public enum State {Idle, Defense, Attack, Walk}

    private State state = State.Idle;


    public boolean setState(State newState) {
        if (this.state != newState){
            this.state = newState;
            updated();
            return true;
        }
        return false;
    }
}
