package com.github.fabiitch.spawner.entity;

public class EntityOperation {

    public enum Type{
        Add, Remove, Destroy
    }
    private int entityId;
    private Type type;
}
