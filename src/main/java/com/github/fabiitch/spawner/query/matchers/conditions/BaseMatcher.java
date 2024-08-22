package com.github.fabiitch.spawner.query.matchers.conditions;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.query.EntityFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseMatcher implements EntityFilter {

    protected Array<EntityFilter> matchers = new Array<>();

    public BaseMatcher(EntityFilter... matchers){
        for (EntityFilter matcher : matchers) {
            this.matchers.add(matcher);
        }
    }

    public BaseMatcher addMatcher(final EntityFilter matcher) {
        matchers.add(matcher);
        return this;
    }

    public BaseMatcher removeMatcher(final EntityFilter matcher) {
        matchers.removeValue(matcher, true);
        return this;
    }

    public void clearMatcher() {
        matchers.clear();
    }
}
