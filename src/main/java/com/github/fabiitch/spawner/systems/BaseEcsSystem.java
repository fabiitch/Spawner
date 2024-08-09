package com.github.fabiitch.spawner.systems;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEcsSystem implements EcsSystem {
    private boolean active = true;
    private int priority;

    public BaseEcsSystem(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
