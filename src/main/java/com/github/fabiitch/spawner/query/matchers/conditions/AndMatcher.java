package com.github.fabiitch.spawner.query.matchers.conditions;

import com.github.fabiitch.spawner.query.EntityFilter;

public class AndMatcher extends BaseMatcher {

    public AndMatcher() {
    }

    public AndMatcher(EntityFilter... matchers) {
        super(matchers);
    }

    @Override
    public boolean accept(int entityId) {
        for (EntityFilter matcher : matchers) {
            if (!matcher.accept(entityId))
                return false;
        }
        return true;
    }
}