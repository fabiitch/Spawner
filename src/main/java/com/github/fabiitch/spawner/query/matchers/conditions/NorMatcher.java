package com.github.fabiitch.spawner.query.matchers.conditions;

import com.github.fabiitch.spawner.query.EntityFilter;

/**
 * no condition should be true
 */
public class NorMatcher extends BaseMatcher {

    public NorMatcher() {
    }

    public NorMatcher(EntityFilter... matchers) {
        super(matchers);
    }

    @Override
    public boolean accept(int entityId) {
        for (EntityFilter matcher : matchers) {
            if (matcher.accept(entityId))
                return false;
        }
        return true;
    }
}
