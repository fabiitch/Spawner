package com.github.fabiitch.spawner.archetype;

import com.github.fabiitch.spawner.archetype.criteria.AspectsMatcher;
import com.github.fabiitch.spawner.archetype.criteria.BehaviorsMatcher;
import com.github.fabiitch.spawner.archetype.criteria.ComponentsMatcher;
import com.github.fabiitch.spawner.archetype.criteria.FlagsMatcher;
import com.github.fabiitch.spawner.impact.AspectImpacted;
import com.github.fabiitch.spawner.impact.BehaviorImpacted;
import com.github.fabiitch.spawner.impact.ComponentImpacted;
import com.github.fabiitch.spawner.impact.FlagImpacted;
import com.github.fabiitch.spawner.query.EntityFilter;

public interface IArchetype extends EntityFilter,
        ComponentImpacted, ComponentsMatcher,
        BehaviorImpacted, BehaviorsMatcher,
        AspectImpacted, AspectsMatcher,
        FlagImpacted, FlagsMatcher {
}

