package com.github.fabiitch.spawner.data.components.defense;


import com.github.fabiitch.spawner.data.behaviors.DefenseBehavior;

public class ArmorComponent extends DefenseBehavior {
    @Override
    public int defense() {
        return 1;
    }
}
