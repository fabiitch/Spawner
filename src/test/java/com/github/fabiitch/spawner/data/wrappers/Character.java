package com.github.fabiitch.spawner.data.wrappers;

import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.data.components.VelocityComponent;
import com.github.fabiitch.spawner.wrapper.EntityWrapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Character implements EntityWrapper {

    private int entityId;

    private PositionComponent position;
    private VelocityComponent velocity;
    private boolean dead;

}
