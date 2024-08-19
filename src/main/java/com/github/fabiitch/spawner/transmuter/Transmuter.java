package com.github.fabiitch.spawner.transmuter;

public abstract class Transmuter<C, D> {

    private TransmuterManager manager;

    protected abstract Transmutation transmute(D data, C component);



}
