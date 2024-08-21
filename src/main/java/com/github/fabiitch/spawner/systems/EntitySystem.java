package com.github.fabiitch.spawner.systems;

import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.Archetype;
import com.github.fabiitch.spawner.impact.BehaviorImpacted;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.impact.FlagImpacted;
import com.github.fabiitch.spawner.family.Family;
import com.github.fabiitch.spawner.utils.sort.EntityComparator;
import com.github.fabiitch.spawner.utils.collections.SafeIntArray;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class EntitySystem extends EngineSystem
        implements ComponentImpacted, BehaviorImpacted, FlagImpacted {

    private final Family family;

    @Getter
    @Setter
    private EntityComparator comparator;

    private final IntArray loopArray = new IntArray();

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
    public boolean impactedByFlag(int indexFlag) {
        return family.impactedByFlag(indexFlag);
    }
}
