package com.github.fabiitch.spawner.data.components.attack;


import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;

public class SwordComponent implements AttackBehavior {
    public int damage;

    public SwordComponent(int damage) {
        this.damage = damage;
    }

    @Override
    public int attack() {
        return damage;
    }
}
