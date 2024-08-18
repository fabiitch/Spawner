package com.github.fabiitch.spawner.data.transmuters;

import com.badlogic.gdx.math.Vector2;
import com.github.fabiitch.spawner.data.components.PositionComponent;
import com.github.fabiitch.spawner.transumter.Transmuter;
import com.github.fabiitch.spawner.transumter.Transmutation;

public class PositionTransmuter extends Transmuter<PositionComponent, Vector2> {
    @Override
    public Transmutation getType() {
        return Transmutation.Modify;
    }

    @Override
    protected boolean transmute(Vector2 data, PositionComponent component) {
         component.set(data);
         return true;
    }

}
