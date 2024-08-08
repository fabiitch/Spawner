package com.github.fabiitch.spawner.listeners;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.github.fabiitch.spawner.entity.EntityManager;
import com.github.fabiitch.spawner.entity.mapper.EntityMapperManager;
import com.github.fabiitch.spawner.family.FamilyManager;
import com.github.fabiitch.spawner.listeners.entity.EntityListener;

public class ListenerManager implements ComponentListener<Object>, BehaviorListener<Object>, FlagListener {
    private final EntityManager entityManager;
    private final FamilyManager familyManager;
    private final EntityMapperManager entityMapperManager;

    private final IntMap<Array<EntityListener>> entityListenersMap = new IntMap<>();
    private final Array<WorldListener> engineListeners = new Array<>();

    public ListenerManager(EntityManager entityManager,
                           FamilyManager familyManager,
                           EntityMapperManager entityMapperManager) {
        this.entityManager = entityManager;
        this.familyManager = familyManager;
        this.entityMapperManager=entityMapperManager;
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
        entityMapperManager.onComponentAdd(entityId, componentIndex);

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
        entityMapperManager.onComponentRemove(entityId, componentIndex);

        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onComponentRemove(componentIndex, component);
            }
        }
    }

    @Override
    public void onBehaviorComponentAdd(int entityId, Object component, int componentIndex) {
        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onBehaviorComponentAdd(componentIndex, component);
            }
        }
    }

    @Override
    public void onBehaviorComponentRemove(int entityId, Object component, int componentIndex) {
        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onBehaviorComponentRemove(componentIndex, component);
            }
        }
    }

    @Override
    public void onBehaviorGet(int entityId, Object componentBehavior, int behaviorIndex) {
        entityManager.onBehaviorGet(entityId, behaviorIndex);
        familyManager.onBehaviorGet(entityId, behaviorIndex);
        entityMapperManager.onBehaviorGet(entityId, behaviorIndex);

        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onBehaviorGet(behaviorIndex, componentBehavior);
            }
        }
    }

    @Override
    public void onBehaviorLoose(int entityId, Object componentBehavior, int behaviorIndex) {
        entityManager.onBehaviorLoose(entityId, behaviorIndex);
        familyManager.onBehaviorLoose(entityId, behaviorIndex);
        entityMapperManager.onBehaviorGet(entityId, behaviorIndex);

        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onBehaviorLoose(behaviorIndex, componentBehavior);
            }
        }
    }

    @Override
    public void onFlagAdd(int entityId, int flagIndex) {
        entityManager.onFlagAdd(entityId, flagIndex);
        familyManager.onFlagAdd(entityId, flagIndex);
        entityMapperManager.onFlagAdd(entityId, flagIndex);

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
        entityMapperManager.onFlagRemove(entityId, flagIndex);

        Array<EntityListener> entityListeners = entityListenersMap.get(entityId);
        if (entityListeners != null) {
            for (EntityListener entityListener : entityListeners) {
                entityListener.onFlagRemove(flagIndex);
            }
        }
    }
}
