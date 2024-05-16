package com.github.fabiitch.spawner.mappers;//package com.github.fabiitch.spawner.mappers;
//
//import com.badlogic.gdx.utils.ObjectIntMap;
//import com.github.fabiitch.spawner.listeners.ListenerManager;
//import com.github.fabiitch.spawner.utils.collections.Tab;
//
//public abstract class BaseManager<M extends BaseMapper, L> {
//    protected final ListenerManager listenerManager;
//    protected int increment = -1;
//
//    protected final ObjectIntMap<Class<?>> indexMap = new ObjectIntMap<>();
//    protected final Tab<M> mappers = new Tab<>();
//
//    public BaseManager(ListenerManager listenerManager) {
//        this.listenerManager = listenerManager;
//    }
//
//    public M getMapper(Class<?> mapperClass) {
//        int componentIndex = indexMap.get(mapperClass, -1);
//        return mappers.get(componentIndex);
//    }
//
//    public M getMapper(int mapperIndex) {
//        return mappers.get(mapperIndex);
//    }
//
//    public boolean existMapper(Class<?> componentClass) {
//        int componentIndex = indexMap.get(componentClass, -1);
//        return componentIndex != -1;
//    }
//
//    public void addInternalListener(int componentIndex, listener) {
//        mappers.get(componentIndex).addInternalListener(listener);
//    }
//
//    public void removeInternalListener(int componentIndex, L listener) {
//        mappers.get(componentIndex).removeInternalListener(listener);
//    }
//}
