package com.github.fabiitch.spawner.data.wrappers;

import com.github.fabiitch.spawner.behavior.BehaviorManager;
import com.github.fabiitch.spawner.component.ComponentManager;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import com.github.fabiitch.spawner.entity.EntityWrapper;
import com.github.fabiitch.spawner.flag.FlagManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnightWrapper implements EntityWrapper {

    private int id;

    private PositionComponent position;
    private SwordComponent swordComponent;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
