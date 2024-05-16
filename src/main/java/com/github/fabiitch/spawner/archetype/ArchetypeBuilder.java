package com.github.fabiitch.spawner.archetype;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.archetype.criteria.QueryCriteria;
import lombok.Getter;

@Getter
public class ArchetypeBuilder {

    private final CriteriaData components = new CriteriaData();
    private final CriteriaData behaviors = new CriteriaData();
    private final CriteriaData flags = new CriteriaData();

    public static ArchetypeBuilder get() {
        return new ArchetypeBuilder();
    }

    public ArchetypeBuilder components(QueryCriteria criteria, Class<?>... componentTypes) {
        add(components, criteria, componentTypes);
        return this;
    }

    public ArchetypeBuilder behaviors(QueryCriteria criteria, Class<?>... componentTypes) {
        add(behaviors, criteria, componentTypes);
        return this;
    }

    public ArchetypeBuilder flags(QueryCriteria criteria, Class<?>... flagTypes) {
        add(flags, criteria, flagTypes);
        return this;
    }

    private void add(CriteriaData data, QueryCriteria criteria, Class<?>... componentTypes) {
        switch (criteria) {
            case OneOf:
                data.oneAccept(componentTypes);
                break;
            case AllOf:
                data.allAccept(componentTypes);
                break;
            case NoneOf:
                data.oneExclude(componentTypes);
                break;
            case NoneAll:
                data.allExclude(componentTypes);
                break;
        }
    }

    @Getter
    static class CriteriaData {
        private final Array<Class<?>> oneAccept = new Array<>();
        private final Array<Class<?>> oneExclude = new Array<>();
        private final Array<Class<?>> allAccept = new Array<>();
        private final Array<Class<?>> allExclude = new Array<>();

        CriteriaData oneAccept(Class<?>... componentTypes) {
            this.oneAccept.addAll(componentTypes);
            return this;
        }

        CriteriaData oneExclude(Class<?>... componentTypes) {
            this.oneExclude.addAll(componentTypes);
            return this;
        }

        CriteriaData allAccept(Class<?>... componentTypes) {
            this.allAccept.addAll(componentTypes);
            return this;
        }

        CriteriaData allExclude(Class<?>... componentTypes) {
            this.allExclude.addAll(componentTypes);
            return this;
        }

        public boolean isEmpty() {
            return oneAccept.isEmpty() && oneExclude.isEmpty() && allAccept.isEmpty() && allExclude.isEmpty();
        }
    }
}
