package com.github.fabiitch.spawner.query.matchers.conditions;

import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;

/**
 * no condition should be true
 */
public class NorMatcher extends BaseMatcher {

    public NorMatcher() {
    }

    public NorMatcher(EntityMatcher... matchers) {
        super(matchers);
    }

    @Override
    public boolean accept(int entityId) {
        for (EntityMatcher matcher : matchers) {
            if (matcher.accept(entityId))
                return false;
        }
        return true;
    }
}
