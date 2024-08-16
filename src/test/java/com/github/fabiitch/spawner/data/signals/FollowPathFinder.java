package com.github.fabiitch.spawner.data.signals;

import com.badlogic.gdx.math.Vector2;

public class FollowPathFinder extends AiPathFinderBehavior {

    private final Vector2 target;
    private final Vector2 lastTarPos = new Vector2();

    public FollowPathFinder(Vector2 target) {
        this.target = target;
        lastTarPos.set(target);
    }

    @Override
    public Vector2 getTarget() {
        return target;
    }

    @Override
    public boolean shouldUpdatePath() {
        return !lastTarPos.epsilonEquals(target, 1);
    }
}
