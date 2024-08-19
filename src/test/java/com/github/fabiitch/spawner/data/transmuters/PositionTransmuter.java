package com.github.fabiitch.spawner.data.transmuters;

import com.badlogic.gdx.math.Vector2;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.transmuter.Transmuter;
import com.github.fabiitch.spawner.transmuter.Transmutation;

public class PositionTransmuter extends Transmuter<PositionComponent, Vector2> {

    @Override
    protected Transmutation transmute(Vector2 data, PositionComponent component) {
         component.set(data);
         return Transmutation.Modify;
    }

}
