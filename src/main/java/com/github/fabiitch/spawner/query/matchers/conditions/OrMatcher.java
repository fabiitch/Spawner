package com.github.fabiitch.spawner.query.matchers.conditions;

import com.github.fabiitch.spawner.query.EntityFilter;

public class OrMatcher extends BaseMatcher {

    public OrMatcher() {
    }

    public OrMatcher(EntityFilter... matchers) {
        super(matchers);
    }

    @Override
    public boolean accept(int entityId) {
        for (EntityFilter matcher : matchers) {
            if (matcher.accept(entityId))
                return true;
        }
        return false;
    }
}
