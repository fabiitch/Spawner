package com.github.fabiitch.spawner.data.components;

import com.badlogic.gdx.math.Vector2;

public class PositionComponent extends Vector2 {

    public PositionComponent() {
    }

    public PositionComponent(float x, float y) {
        super(x, y);
    }

    public PositionComponent(Vector2 v) {
        super(v);
    }
}
