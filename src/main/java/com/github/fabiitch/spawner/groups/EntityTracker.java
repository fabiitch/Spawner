package com.github.fabiitch.spawner.groups;

import com.github.fabiitch.spawner.archetype.criteria.BehaviorImpacted;
import com.github.fabiitch.spawner.archetype.criteria.ComponentImpacted;
import com.github.fabiitch.spawner.query.EntityFilter;
import com.github.fabiitch.spawner.archetype.criteria.FlagImpacted;

public interface EntityTracker extends EntityFilter, ComponentImpacted, BehaviorImpacted, FlagImpacted {
}
