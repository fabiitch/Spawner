package com.github.fabiitch.spawner.data.wrappers;

import com.github.fabiitch.spawner.data.behaviors.AttackBehavior;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.entity.EntityWrapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Knight implements EntityWrapper {

    private int id;

    private PositionComponent position;
    private SwordComponent swordComponent;

    private AttackBehavior attackBehavior;
    private boolean dead;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
