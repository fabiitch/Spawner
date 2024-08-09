package com.github.fabiitch.spawner.query.matchers.conditions;

import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;

public class OrMatcher extends BaseMatcher {

    @Override
    public boolean accept(int entityId) {
        for (EntityMatcher matcher : matchers) {
            if (matcher.accept(entityId))
                return true;
        }
        return false;
    }
}
