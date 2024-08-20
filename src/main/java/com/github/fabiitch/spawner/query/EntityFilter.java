package com.github.fabiitch.spawner.query;

public interface EntityFilter {
    boolean accept(int entityId);
}
