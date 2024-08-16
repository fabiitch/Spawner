package com.github.fabiitch.spawner.data.signals;

import com.badlogic.gdx.math.Vector2;

public class SimplePathFinder extends AiPathFinderBehavior {

    private final Vector2 currentPosition = new Vector2();
    private final Vector2 targetPosition = new Vector2();

    public SimplePathFinder(float x, float y) {
        targetPosition.set(x,y);
    }

    public void setPosition(float x, float y){
        this.currentPosition.set(x,y);
        updated();
    }

    @Override
    public Vector2 getTarget() {
        return currentPosition;
    }

    @Override
    public boolean shouldUpdatePath() {
        return currentPosition.epsilonEquals(targetPosition, 1);
    }
}
