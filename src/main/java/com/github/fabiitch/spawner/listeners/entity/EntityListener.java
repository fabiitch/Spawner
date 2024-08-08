package com.github.fabiitch.spawner.listeners.entity;

/**
 * Listener is removed from engine after entity destroy
 */
public interface EntityListener extends EntityComponentListener<Object>, EntityBehaviorListener<Object>, EntityFlagListener {

    void onAdd();

    void onRemove();

    void onDestroy();
}
