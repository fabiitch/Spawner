package com.github.fabiitch.spawner.data.wrappers;

import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Knight extends Character {

    private int id;

    private PositionComponent position;
    private SwordComponent swordComponent;

    private AttackBehavior attackBehavior;

}
