package com.github.fabiitch.spawner.query.matchers.conditions;

import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;

/**
 * Only One condition should be true
 */
public class XorMatcher extends BaseMatcher {
    @Override
    public boolean accept(int entityId) {
        int matcherTrue = 0;
        for (EntityMatcher matcher : matchers) {
            if (matcher.accept(entityId))
                matcherTrue++;
            if (matcherTrue > 1)
                return false;
        }
        return matcherTrue == 1;
    }
}
