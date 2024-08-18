package com.github.fabiitch.spawner.transumter;

import com.badlogic.gdx.utils.Pool;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transmute implements Pool.Poolable {
    private Transmutation transmutation;
    private int entityId;

    private int index;
    public void reset() {

    }


}
