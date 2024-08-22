package com.github.fabiitch.spawner.archetype.builder;

import com.badlogic.gdx.utils.Array;
import com.github.fabiitch.spawner.WorldConfig;
import com.github.fabiitch.spawner.archetype.ArchetypeBuilderException;
import com.github.fabiitch.spawner.EntityPart;
import com.github.fabiitch.spawner.archetype.IArchetype;
import com.github.fabiitch.spawner.archetype.criteria.QueryCriteria;
import lombok.Getter;

@Getter
public class ArchetypeBuilder {

    private final CriteriaData components = new CriteriaData();
    private final CriteriaData behaviors = new CriteriaData();
    private final CriteriaData aspect = new CriteriaData();
    private final CriteriaData flags = new CriteriaData();

    public static ArchetypeBuilder get() {
        return new ArchetypeBuilder();
    }

    public ArchetypeBuilder hasComponent(Class<?> component) {
        add(components, QueryCriteria.AllOf, component);
        return this;
    }

    public ArchetypeBuilder components(QueryCriteria criteria, Class<?>... componentTypes) {
        add(components, criteria, componentTypes);
        return this;
    }

    public ArchetypeBuilder behaviors(QueryCriteria criteria, Class<?>... componentTypes) {
        add(behaviors, criteria, componentTypes);
        return this;
    }

    public ArchetypeBuilder aspect(QueryCriteria criteria, Class<?>... aspectType) {
        add(aspect, criteria, aspectType);
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

    public IArchetype register(WorldConfig config) {
        return config.registerArchetype(this);
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

        public void isValid(EntityPart part) {
            for (Class<?> classOneAccepted : oneAccept) {
                if (oneExclude.contains(classOneAccepted, true))
                    throw new ArchetypeBuilderException(part + " : one accept intersect one exclude on " + classOneAccepted.getSimpleName());
                if (allAccept.contains(classOneAccepted, true))
                    throw new ArchetypeBuilderException(part + " : one accept intersect all accepted on " + classOneAccepted.getSimpleName());
            }
            for (Class<?> classOneExcluded : oneExclude) {
                if (allExclude.contains(classOneExcluded, true))
                    throw new ArchetypeBuilderException(part + " : one exclude intersect all excluded on " + classOneExcluded.getSimpleName());
            }
        }
    }
}
