package com.github.fabiitch.spawner.listeners.flag;

public interface FlagRemoveListener {
    /**
     * Not called when EntityReference is removed or destroy
     */
    void onFlagRemove(int entityId, int flagIndex);
}
