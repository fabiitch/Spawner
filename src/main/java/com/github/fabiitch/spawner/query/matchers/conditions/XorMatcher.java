package com.github.fabiitch.spawner.query.matchers.conditions;

import com.github.fabiitch.spawner.query.EntityFilter;

/**
 * Only One condition should be true
 */
public class XorMatcher extends BaseMatcher {

    public XorMatcher() {
    }

    public XorMatcher(EntityFilter... matchers) {
        super(matchers);
    }

    @Override
    public boolean accept(int entityId) {
        int matcherTrue = 0;
        for (EntityFilter matcher : matchers) {
            if (matcher.accept(entityId))
                matcherTrue++;
            if (matcherTrue > 1)
                return false;
        }
        return matcherTrue == 1;
    }
}
