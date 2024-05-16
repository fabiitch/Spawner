package com.github.fabiitch.spawner.archetype.criteria;

import com.badlogic.gdx.utils.Bits;

public interface ComponentsMatcher {

    boolean componentsMatch(Bits componentsBits);
}
