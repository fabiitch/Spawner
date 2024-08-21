package com.github.fabiitch.spawner.archetype.criteria;

import com.github.fabiitch.spawner.impact.BehaviorImpacted;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.impact.FlagImpacted;
import com.github.fabiitch.spawner.query.EntityFilter;

public interface EntityFilterTracker extends EntityFilter, ComponentImpacted, BehaviorImpacted, FlagImpacted {



}
