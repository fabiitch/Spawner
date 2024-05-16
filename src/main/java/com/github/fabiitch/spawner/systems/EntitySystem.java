package com.github.fabiitch.spawner.systems;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.Archetype;
import com.github.fabiitch.spawner.archetype.criteria.BehaviorImpacted;
import com.github.fabiitch.spawner.archetype.criteria.ComponentImpacted;
import com.github.fabiitch.spawner.archetype.criteria.FlagImpacted;
import com.github.fabiitch.spawner.family.Family;
import lombok.Getter;

@Getter
public abstract class EntitySystem extends EngineSystem
        implements ComponentImpacted, BehaviorImpacted, FlagImpacted {

    private final Family family;

    public EntitySystem(Family family, int priority) {
        super(priority);
        this.family = family;
    }

    public EntitySystem(Archetype archetype, int priority) {
        super(priority);
        this.family = new Family(archetype);
    }

    @Override
    public void update(float dt) {
        IntArray array = family.getEntities();
        for (int i = 0; i < array.size; ++i) {
            int entityId = array.get(i);
            processEntity(entityId, dt);
        }
    }

    protected abstract void processEntity(int entityId, float dt);

    public IntArray getEntities() {
        return family.getEntities();
    }


    @Override
    public boolean impactedByComponent(int indexComponent) {
        return family.impactedByComponent(indexComponent);
    }

    @Override
    public boolean impactedByBehavior(int behaviorIndex) {
        return family.impactedByBehavior(behaviorIndex);
    }

    @Override
    public boolean impactedByFlag(int flagIndex) {
        return family.impactedByFlag(flagIndex);
    }
}
