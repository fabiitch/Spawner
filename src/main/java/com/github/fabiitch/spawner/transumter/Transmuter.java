package com.github.fabiitch.spawner.transumter;

public abstract class Transmuter<C, D> {

    private TransmuterManager manager;

    public abstract Transmutation getType();
    protected abstract boolean transmute(D data, C component);



}
