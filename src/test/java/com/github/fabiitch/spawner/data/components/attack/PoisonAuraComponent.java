package com.github.fabiitch.spawner.data.components.attack;


import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;

public class PoisonAuraComponent implements AttackBehavior {
    @Override
    public int attack() {
        return 100;
    }
}
