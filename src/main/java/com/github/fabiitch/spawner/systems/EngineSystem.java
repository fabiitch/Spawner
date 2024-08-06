package com.github.fabiitch.spawner.systems;

import com.github.fabiitch.spawner.World;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class EngineSystem implements EcsSystem {

    private World world;
    private int priority;
    private boolean active = true;

    public EngineSystem(int priority) {
        this.priority = priority;
    }
}
