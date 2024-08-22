package com.github.fabiitch.spawner.operation.locker;

public interface EcsDataTypeLocker {

    void lock(EcsDataTypeLock ecsDataTypeLock);

    void unLock(EcsDataTypeLock ecsDataTypeLock);
}
