package com.github.fabiitch.spawner.utils.collections;

import com.badlogic.gdx.utils.Bits;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SafeBits{
    private final Bits bits;

    public boolean get(int index) {
        return bits.get(index);
    }
}
