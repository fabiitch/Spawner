package com.github.fabiitch.spawner.archetype.builder;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.fabiitch.spawner.archetype.IArchetype;
import com.github.fabiitch.spawner.DataType;
import com.github.fabiitch.spawner.aspect.AspectManager;
import com.github.fabiitch.spawner.behavior.BehaviorManager;
import com.github.fabiitch.spawner.component.ComponentManager;
import com.github.fabiitch.spawner.entity.EntityManager;
import com.github.fabiitch.spawner.flag.FlagManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArchetypeProcessor {
    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final BehaviorManager behaviorManager;
    private final AspectManager aspectManager;
    private final FlagManager flagManager;

    public IArchetype build(ArchetypeBuilder archetypeBuilder) {

        BitsCriteria componentCriteria = null;
        BitsCriteria behaviorCriteria = null;
        BitsCriteria aspectCriteria = null;
        BitsCriteria flagCriteria = null;

        if (!archetypeBuilder.getComponents().isEmpty()) {
            ArchetypeBuilder.CriteriaData componentsData = archetypeBuilder.getComponents();
            componentsData.isValid(DataType.Component);

            Bits bitsOneAccept = componentsData.getOneAccept().isEmpty() ? null : transformComponents(componentsData.getOneAccept());
            Bits bitsOneExclude = componentsData.getOneExclude().isEmpty() ? null : transformComponents(componentsData.getOneExclude());
            Bits bitsAllAccept = componentsData.getAllAccept().isEmpty() ? null : transformComponents(componentsData.getAllAccept());
            Bits bitsAllExclude = componentsData.getAllExclude().isEmpty() ? null : transformComponents(componentsData.getAllExclude());

            componentCriteria = new BitsCriteria(bitsOneAccept, bitsOneExclude, bitsAllAccept, bitsAllExclude);
        }

        if (!archetypeBuilder.getBehaviors().isEmpty()) {
            ArchetypeBuilder.CriteriaData behaviorsData = archetypeBuilder.getBehaviors();
            behaviorsData.isValid(DataType.Behavior);

            Bits bitsOneAccept = behaviorsData.getOneAccept().isEmpty() ? null : transformBehaviors(behaviorsData.getOneAccept());
            Bits bitsOneExclude = behaviorsData.getOneExclude().isEmpty() ? null : transformBehaviors(behaviorsData.getOneExclude());
            Bits bitsAllAccept = behaviorsData.getAllAccept().isEmpty() ? null : transformBehaviors(behaviorsData.getAllAccept());
            Bits bitsAllExclude = behaviorsData.getAllExclude().isEmpty() ? null : transformBehaviors(behaviorsData.getAllExclude());

            behaviorCriteria = new BitsCriteria(bitsOneAccept, bitsOneExclude, bitsAllAccept, bitsAllExclude);
        }
        if (!archetypeBuilder.getAspect().isEmpty()) {
            ArchetypeBuilder.CriteriaData aspectData = archetypeBuilder.getAspect();
            aspectData.isValid(DataType.Aspect);

            Bits bitsOneAccept = aspectData.getOneAccept().isEmpty() ? null : transformAspects(aspectData.getOneAccept());
            Bits bitsOneExclude = aspectData.getOneExclude().isEmpty() ? null : transformAspects(aspectData.getOneExclude());
            Bits bitsAllAccept = aspectData.getAllAccept().isEmpty() ? null : transformAspects(aspectData.getAllAccept());
            Bits bitsAllExclude = aspectData.getAllExclude().isEmpty() ? null : transformAspects(aspectData.getAllExclude());

            aspectCriteria = new BitsCriteria(bitsOneAccept, bitsOneExclude, bitsAllAccept, bitsAllExclude);
        }

        if (!archetypeBuilder.getFlags().isEmpty()) {
            ArchetypeBuilder.CriteriaData flagsData = archetypeBuilder.getFlags();
            flagsData.isValid(DataType.Flag);

            Bits bitsOneAccept = flagsData.getOneAccept().isEmpty() ? null : transformFlags(flagsData.getOneAccept());
            Bits bitsOneExclude = flagsData.getOneExclude().isEmpty() ? null : transformFlags(flagsData.getOneExclude());
            Bits bitsAllAccept = flagsData.getAllAccept().isEmpty() ? null : transformFlags(flagsData.getAllAccept());
            Bits bitsAllExclude = flagsData.getAllExclude().isEmpty() ? null : transformFlags(flagsData.getAllExclude());

            flagCriteria = new BitsCriteria(bitsOneAccept, bitsOneExclude, bitsAllAccept, bitsAllExclude);
        }
        return new Archetype(entityManager, componentCriteria, behaviorCriteria, aspectCriteria, flagCriteria);
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

    private Bits transformAspects(Array<Class<?>> classToMap) {
        Bits bits = new Bits();
        for (Class<?> aClass : classToMap) {
            if (aspectManager.existMapper(aClass)) {
                int indexAspect = aspectManager.getMapper(aClass).getIndex();
                bits.set(indexAspect);
            } else {
                throw new GdxRuntimeException("Aspect class : " + aClass.getName() + " is not register");
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
