package com.github.fabiitch.spawner.listeners;

/**
 * Listener is removed from engine after entity destroy
 */
public interface EntityListener {

    void onAdd();

    void onRemove();

    void onDestroy();

    void onFlagAdd(int flagIndex);

    void onFlagRemove(int flagIndex);

    void onComponentAdd(int componentIndex, Object component);

    void onComponentRemove(int componentIndex, Object component);

    void onBehaviorGet(int indexBehavior, Object componentBehavior);

    void onBehaviorLoose(int indexBehavior, Object componentBehavior);
}
