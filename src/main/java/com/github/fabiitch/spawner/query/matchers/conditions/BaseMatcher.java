package com.github.fabiitch.spawner.query.matchers.conditions;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.archetype.criteria.EntityMatcher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseMatcher implements EntityMatcher {

    protected Array<EntityMatcher> matchers = new Array<>();

    public BaseMatcher addMatcher(final EntityMatcher matcher) {
        matchers.add(matcher);
        return this;
    }

    public BaseMatcher removeMatcher(final EntityMatcher matcher) {
        matchers.removeValue(matcher, true);
        return this;
    }

    public void clearMatcher() {
        matchers.clear();
    }
}
