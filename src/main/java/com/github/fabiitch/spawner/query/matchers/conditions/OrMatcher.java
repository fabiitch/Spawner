package com.github.fabiitch.spawner.query.matchers.conditions;

import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;

public class OrMatcher extends BaseMatcher {

    public OrMatcher() {
    }

    public OrMatcher(EntityMatcher... matchers) {
        super(matchers);
    }

    @Override
    public boolean accept(int entityId) {
        for (EntityMatcher matcher : matchers) {
            if (matcher.accept(entityId))
                return true;
        }
        return false;
    }
}
