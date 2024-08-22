package com.github.fabiitch.spawner.systems;

import com.github.fabiitch.spawner.archetype.IArchetype;
import com.github.fabiitch.spawner.family.Family;
import com.github.fabiitch.spawner.impact.AspectImpacted;
import com.github.fabiitch.spawner.impact.BehaviorImpacted;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.impact.FlagImpacted;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import com.github.fabiitch.spawner.utils.sort.EntityComparator;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class EntitySystem extends EngineSystem
        implements ComponentImpacted, BehaviorImpacted, AspectImpacted, FlagImpacted {
    private final Family family;

    @Getter
    @Setter
    private EntityComparator comparator;

    public EntitySystem(Family family, int priority) {
        super(priority);
        this.family = family;
    }

    public EntitySystem(IArchetype archetype, int priority) {
        super(priority);
        this.family = new Family(archetype);
    }

    @Override
    public void update(float dt) {
        SafeIntArray array = family.getEntities();
        if (comparator != null)
            family.sort(comparator);

        for (int i = 0, n = array.size(); i < n; ++i) {
            int entityId = array.get(i);
            processEntity(entityId, dt);
        }
    }

    protected abstract void processEntity(int entityId, float dt);

    public SafeIntArray getEntities() {
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
    public boolean impactedByAspect(int indexAspect) {
        return family.impactedByFlag(indexAspect);
    }

    @Override
    public boolean impactedByFlag(int indexFlag) {
        return family.impactedByFlag(indexFlag);
    }
}
