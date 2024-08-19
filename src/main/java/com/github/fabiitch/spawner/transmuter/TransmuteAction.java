package com.github.fabiitch.spawner.transmuter;

import com.badlogic.gdx.utils.Pool;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransmuteAction implements Pool.Poolable {
    private Transmutation transmutation;
    private int entityId;

    private int index;
    private TransmuteTarget target;


    public void reset() {

    }


}
