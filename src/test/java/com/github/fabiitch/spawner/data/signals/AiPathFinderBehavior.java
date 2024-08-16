package com.github.fabiitch.spawner.data.signals;

import com.badlogic.gdx.math.Vector2;
import com.github.fabiitch.spawner.signals.SignalData;

public abstract class AiPathFinderBehavior extends SignalData {


    public abstract Vector2 getTarget();

    public abstract boolean shouldUpdatePath();

    @Override
    public void updated() {
        if (shouldUpdatePath())
            super.updated();
    }
}
