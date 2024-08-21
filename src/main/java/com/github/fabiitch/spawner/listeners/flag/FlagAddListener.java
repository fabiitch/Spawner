package com.github.fabiitch.spawner.listeners.flag;

public interface FlagAddListener {
    /**
     * Not called when EntityReference is added in world
     */
    void onFlagAdd(int entityId, int flagIndex);
}
