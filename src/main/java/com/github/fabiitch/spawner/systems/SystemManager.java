package com.github.fabiitch.spawner.systems;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.World;
import com.github.fabiitch.spawner.behavior.BehaviorManager;
import com.github.fabiitch.spawner.component.ComponentManager;

import java.util.Comparator;

public class SystemManager {
    private final World world;

    private final Array<EcsSystem> systems = new Array<>();
    private final SystemComparator systemComparator = new SystemComparator();

    public SystemManager(World world) {
        this.world = world;
    }

    public void addSystem(EcsSystem system) {
        if (world.isUpdating()) {
            int priority = system.getPriority();
            if (priority < systems.get(currentLoopIndex).getPriority())
                currentLoopIndex++;
        }
        systems.add(system);
        systems.sort(systemComparator);      //TODO threading, fix sort
    }

    public boolean removeSystem(EcsSystem system) {
        if (world.isUpdating()) {
            int priority = system.getPriority();
            if (priority < systems.get(currentLoopIndex).getPriority())
                currentLoopIndex--;
        }
        return systems.removeValue(system, true);
    }

    private int currentLoopIndex;

    public EcsSystem loop() {
        if (currentLoopIndex >= systems.size) {
            currentLoopIndex = 0;
            return null;
        }
        EcsSystem system = systems.get(currentLoopIndex);
        currentLoopIndex++;
        if (!system.isActive())
            return loop();
        return system;
    }

    public EcsSystem getCurrentSystem() {
        return systems.get(currentLoopIndex);
    }


    private static class SystemComparator implements Comparator<EcsSystem> {
        @Override
        public int compare(EcsSystem a, EcsSystem b) {
            return a.getPriority() - b.getPriority();
        }
    }
}
