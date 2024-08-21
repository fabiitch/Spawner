package com.github.fabiitch.spawner.listeners.component;

public interface ComponentRemoveListener<C> {

    /**
     * Not called when EntityReference is removed or destroy
     */
    void onComponentRemove(int entityId, C component, int componentIndex);

}
