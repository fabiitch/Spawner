package com.github.fabiitch.spawner.listeners;

import com.github.fabiitch.spawner.listeners.entity.EntityBehaviorListener;
import com.github.fabiitch.spawner.listeners.entity.EntityComponentListener;
import com.github.fabiitch.spawner.listeners.entity.EntityFlagListener;

/**
 * Listener is removed from engine after entity destroy
 */
public interface EntityListener extends EntityComponentListener<Object>, EntityBehaviorListener<Object>, EntityFlagListener {

    void onAdd();

    void onRemove();

    void onDestroy();
}
