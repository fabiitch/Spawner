package com.github.fabiitch.spawner.listeners;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.github.fabiitch.spawner.entity.EntityManager;
import com.github.fabiitch.spawner.family.FamilyManager;
import com.github.fabiitch.spawner.listeners.entity.EntityListener;
import com.github.fabiitch.spawner.wrapper.EntityWrapperManager;

public class ListenerManager implements ComponentListener<Object>, BehaviorListener<Object>, FlagListener {
    private final EntityManager entityManager;
    private final FamilyManager familyManager;
    private final EntityWrapperManager entityWrapperManager;

    private final IntMap<Array<EntityListener>> entityListenersMap = new IntMap<>();
    private final Array<WorldListener> engineListeners = new Array<>();

    public ListenerManager(EntityManager entityManager,
                           FamilyManager familyManager,
                           EntityWrapperManager entityWrapperManager) {
        this.entityManager = entityManager;
        this.familyManager = familyManager;
        this.entityWrapperManager = entityWrapperManager;
    }

    public void onEntityCreate(int entityId) {
        for (WorldListener listener : engineListeners) {
            listener.onEntityCreate(entityId);
        }
    }

    public void onEntityAdd(int entityId) {
        for (WorldListener listener : engineListeners) {
            listener.onEntityAdd(entityId);
        }
        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onAdd();
            }
        }
    }

    public void onEntityRemove(int entityId) {
        for (WorldListener listener : engineListeners) {
            listener.onEntityRemove(entityId);
        }
        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onRemove();
            }
        }
    }

    public void onEntityDestroy(int entityId) {
        for (WorldListener listener : engineListeners) {
            listener.onEntityDestroy(entityId);
        }
        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onDestroy();
            }
            entityListenersMap.remove(entityId);
        }
    }

    public void addEngineListener(WorldListener worldListener) {
        engineListeners.add(worldListener);
    }

    public void removeEngineListener(WorldListener worldListener) {
        engineListeners.removeValue(worldListener, true);
    }

    public boolean addEntityListener(int entityId, EntityListener entityListener) {
        if (entityManager.exist(entityId)) {
            Array<EntityListener> listenersForEntity = entityListenersMap.get(entityId);
            if (listenersForEntity == null) {
                listenersForEntity = new Array<>();
                entityListenersMap.put(entityId, listenersForEntity);
            }
            listenersForEntity.add(entityListener);

            return true;
        }
        return false;
    }

    public boolean removeEntityListener(int entityId, EntityListener entityListener) {
        if (entityManager.exist(entityId)) {
            Array<EntityListener> listenersForEntity = entityListenersMap.get(entityId);
            if (listenersForEntity == null) {
                return false;
            }
            return listenersForEntity.removeValue(entityListener, true);
        }
        return false;
    }

    @Override
    public void onComponentAdd(int entityId, Object component, int componentIndex) {
        entityManager.onComponentAdd(entityId, componentIndex);
        familyManager.onComponentAdd(entityId, componentIndex);
        entityWrapperManager.onComponentAdd(entityId, componentIndex);

        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onComponentAdd(componentIndex, component);
            }
        }
    }

    @Override
    public void onComponentRemove(int entityId, Object component, int componentIndex) {
        entityManager.onComponentRemove(entityId, componentIndex);
        familyManager.onComponentRemove(entityId, componentIndex);
        entityWrapperManager.onComponentRemove(entityId, componentIndex);

        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onComponentRemove(componentIndex, component);
            }
        }
    }

    @Override
    public void onComponentUpdate(int entityId, Object component, int componentIndex) {
        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onComponentUpdate(componentIndex, component);
            }
        }
    }

    @Override
    public void onBehaviorComponentAdd(int entityId, Object component, int behaviorIndex, int componentIndex) {
        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onBehaviorComponentAdd(behaviorIndex, component, componentIndex);
            }
        }
    }

    @Override
    public void onBehaviorComponentRemove(int entityId, Object component, int behaviorIndex, int componentIndex) {
        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onBehaviorComponentRemove(behaviorIndex, component, componentIndex);
            }
        }
    }

    @Override
    public void onBehaviorUpdate(int entityId, Object component, int behaviorIndex, int componentIndex) {
        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onBehaviorComponentUpdate(behaviorIndex, component, componentIndex);
            }
        }
    }

    @Override
    public void onBehaviorGet(int entityId, Object componentBehavior, int behaviorIndex, int componentIndex) {
        entityManager.onBehaviorGet(entityId, behaviorIndex);
        familyManager.onBehaviorGet(entityId, behaviorIndex);
        entityWrapperManager.onBehaviorGet(entityId, behaviorIndex);

        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onBehaviorGet(behaviorIndex, componentBehavior, componentIndex);
            }
        }
    }

    @Override
    public void onBehaviorLoose(int entityId, Object componentBehavior,int behaviorIndex, int componentIndex) {
        entityManager.onBehaviorLoose(entityId, componentIndex);
        familyManager.onBehaviorLoose(entityId, componentIndex);
        entityWrapperManager.onBehaviorLoose(entityId, componentIndex);

        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onBehaviorLoose(behaviorIndex, componentBehavior, componentIndex);
            }
        }
    }

    @Override
    public void onFlagAdd(int entityId, int flagIndex) {
        entityManager.onFlagAdd(entityId, flagIndex);
        familyManager.onFlagAdd(entityId, flagIndex);
        entityWrapperManager.onFlagAdd(entityId, flagIndex);

        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onFlagAdd(flagIndex);
            }
        }
    }

    @Override
    public void onFlagRemove(int entityId, int flagIndex) {
        entityManager.onFlagRemove(entityId, flagIndex);
        familyManager.onFlagRemove(entityId, flagIndex);
        entityWrapperManager.onFlagRemove(entityId, flagIndex);

        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onFlagRemove(flagIndex);
            }
        }
    }
}
