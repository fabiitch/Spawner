package com.github.fabiitch.spawner.archetype;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.fabiitch.spawner.behavior.BehaviorManager;
import com.github.fabiitch.spawner.component.ComponentManager;
import com.github.fabiitch.spawner.entity.EntityManager;
import com.github.fabiitch.spawner.flag.FlagManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArchetypeManager {
    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final BehaviorManager behaviorManager;
    private final FlagManager flagManager;

    public Archetype build(ArchetypeBuilder archetypeBuilder) {

        BitsCriteria componentFilterData = null;
        BitsCriteria behaviorFilterData = null;
        BitsCriteria flagFilterData = null;

        if (!archetypeBuilder.getComponents().isEmpty()) {
            ArchetypeBuilder.CriteriaData componentsData = archetypeBuilder.getComponents();

            Bits bitsOneAccept = componentsData.getOneAccept().isEmpty() ? null : transformComponents(componentsData.getOneAccept());
            Bits bitsOneExclude = componentsData.getOneExclude().isEmpty() ? null : transformComponents(componentsData.getOneExclude());
            Bits bitsAllAccept = componentsData.getAllAccept().isEmpty() ? null : transformComponents(componentsData.getAllAccept());
            Bits bitsAllExclude = componentsData.getAllExclude().isEmpty() ? null : transformComponents(componentsData.getAllExclude());

            componentFilterData = new BitsCriteria(bitsOneAccept, bitsOneExclude, bitsAllAccept, bitsAllExclude);
        }

        if (!archetypeBuilder.getBehaviors().isEmpty()) {
            ArchetypeBuilder.CriteriaData behaviorsData = archetypeBuilder.getBehaviors();

            Bits bitsOneAccept = behaviorsData.getOneAccept().isEmpty() ? null : transformBehaviors(behaviorsData.getOneAccept());
            Bits bitsOneExclude = behaviorsData.getOneExclude().isEmpty() ? null : transformBehaviors(behaviorsData.getOneExclude());
            Bits bitsAllAccept = behaviorsData.getAllAccept().isEmpty() ? null : transformBehaviors(behaviorsData.getAllAccept());
            Bits bitsAllExclude = behaviorsData.getAllExclude().isEmpty() ? null : transformBehaviors(behaviorsData.getAllExclude());

            behaviorFilterData = new BitsCriteria(bitsOneAccept, bitsOneExclude, bitsAllAccept, bitsAllExclude);
        }

        if (!archetypeBuilder.getFlags().isEmpty()) {
            ArchetypeBuilder.CriteriaData flagsData = archetypeBuilder.getFlags();

            Bits bitsOneAccept = flagsData.getOneAccept().isEmpty() ? null : transformFlags(flagsData.getOneAccept());
            Bits bitsOneExclude = flagsData.getOneExclude().isEmpty() ? null : transformFlags(flagsData.getOneExclude());
            Bits bitsAllAccept = flagsData.getAllAccept().isEmpty() ? null : transformFlags(flagsData.getAllAccept());
            Bits bitsAllExclude = flagsData.getAllExclude().isEmpty() ? null : transformFlags(flagsData.getAllExclude());

            flagFilterData = new BitsCriteria(bitsOneAccept, bitsOneExclude, bitsAllAccept, bitsAllExclude);
        }
        return new Archetype(entityManager, componentFilterData, behaviorFilterData, flagFilterData);
    }

    private Bits transform(Array<Class<?>> classToMap, int type) {
        Bits bits = new Bits();
        for (Class<?> aClass : classToMap) {
            if (componentManager.existMapper(aClass)) {
                int indexToGet = 0;
                if (type == 0)
                    indexToGet = componentManager.getMapper(aClass).getIndex();
                if (type == 1)
                    indexToGet = behaviorManager.getMapper(aClass).getIndex();
                if (type == 2)
                    indexToGet = flagManager.getMapper(aClass).getIndex();
                bits.set(indexToGet);
            } else {
                throw new GdxRuntimeException("Component class : " + aClass.getName() + " is not register");
            }
        }
        return bits;
    }

    private Bits transformComponents(Array<Class<?>> classToMap) {
        Bits bits = new Bits();
        for (Class<?> aClass : classToMap) {
            if (componentManager.existMapper(aClass)) {
                int componentIndex = componentManager.getMapper(aClass).getIndex();
                bits.set(componentIndex);
            } else {
                throw new GdxRuntimeException("Component class : " + aClass.getName() + " is not register");
            }
        }
        return bits;
    }

    private Bits transformBehaviors(Array<Class<?>> classToMap) {
        Bits bits = new Bits();
        for (Class<?> aClass : classToMap) {
            if (behaviorManager.existMapper(aClass)) {
                int behaviorIndex = behaviorManager.getMapper(aClass).getIndex();
                bits.set(behaviorIndex);
            } else {
                throw new GdxRuntimeException("Behavior class : " + aClass.getName() + " is not register");
            }
        }
        return bits;
    }

    private Bits transformFlags(Array<Class<?>> classToMap) {
        Bits bits = new Bits();
        for (Class<?> aClass : classToMap) {
            if (flagManager.existMapper(aClass)) {
                int flagIndex = flagManager.getMapper(aClass).getIndex();
                bits.set(flagIndex);
            } else {
                throw new GdxRuntimeException("Flag class : " + aClass.getName() + " is not register");
            }
        }
        return bits;
    }

}
