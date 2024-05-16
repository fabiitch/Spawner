package com.github.fabiitch.spawner.systems;

public abstract class BaseEcsSystem implements EcsSystem {
    private boolean active = true;
    private int priority;

    public BaseEcsSystem() {
    }

    public BaseEcsSystem(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }


    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

}
