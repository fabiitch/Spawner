package com.github.fabiitch.spawner.data.components.attack;


import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwordComponent implements AttackBehavior {
    private int damage;

    public SwordComponent(int damage) {
        this.damage = damage;
    }

    @Override
    public int attack() {
        return damage;
    }
}
