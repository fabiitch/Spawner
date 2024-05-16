package com.github.fabiitch.spawner.listeners;

public interface FlagListener {

    void onFlagAdd(int entityId, int flagIndex);

    void onFlagRemove(int entityId, int flagIndex);
}
