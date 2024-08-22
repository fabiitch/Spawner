package com.github.fabiitch.spawner.data.systems;

import com.github.fabiitch.spawner.archetype.IArchetype;
import com.github.fabiitch.spawner.component.ComponentMapper;
import com.github.fabiitch.spawner.systems.EntitySystem;
import com.github.fabiitch.spawner.BaseTest;
import com.github.fabiitch.spawner.data.components.attack.SwordComponent;
import org.junit.jupiter.api.Assertions;

public class SwordSystem extends EntitySystem {
    private final ComponentMapper<SwordComponent> swordMapper;

    public int callCount;
    public int entityCallCount;

    public SwordSystem(IArchetype swordArchetype) {
        super(swordArchetype, 0);
        swordMapper = BaseTest.swordMapper;
    }

    @Override
    protected void processEntity(int entityId, float dt) {
        SwordComponent swordComponent = swordMapper.getComponent(entityId);
        Assertions.assertNotNull(swordComponent);
        entityCallCount++;
    }


    @Override
    public void update(float dt) {
        super.update(dt);
        callCount++;
    }


}
