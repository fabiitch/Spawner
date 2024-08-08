package com.github.fabiitch.spawner.data.components.attack;


import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class KnifeComponent implements AttackBehavior {
    private int dmg = 1;

    public KnifeComponent(int dmg) {
        this.dmg = dmg;
    }

    @Override
    public int attack() {
        return dmg;
    }
}
