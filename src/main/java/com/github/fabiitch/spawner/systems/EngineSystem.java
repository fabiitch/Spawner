package com.github.fabiitch.spawner.systems;

import com.github.fabiitch.spawner.World;
import lombok.Getter;
@Getter
public abstract class EngineSystem implements EcsSystem {

    private World world;
    private int priority;
    private boolean active = true;

    public EngineSystem(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public World getSpawner() {
        return world;
    }

    public void setSpawner(World world) {
        this.world = world;
    }
}
