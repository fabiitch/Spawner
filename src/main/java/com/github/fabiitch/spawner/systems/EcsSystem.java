package com.github.fabiitch.spawner.systems;

public interface EcsSystem {

    int getPriority();

    void update(float dt);

    boolean isActive();

    void setActive(boolean active);
}
