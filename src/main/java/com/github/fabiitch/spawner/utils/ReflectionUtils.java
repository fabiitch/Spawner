package com.github.fabiitch.spawner.utils;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.github.fabiitch.spawner.signals.SignalData;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReflectionUtils {

    public static boolean isSignalComponent(Class<?> aClass){
        return ClassReflection.isAssignableFrom(SignalData.class, aClass);
    }
}
