package com.github.fabiitch.spawner.utils.mappers;

import com.badlogic.gdx.utils.Array;
import lombok.Getter;

public abstract class BaseMapper<L> {

    @Getter
    protected final int index;

    protected final Array<L> internalListeners = new Array<>();
    @Getter
    protected final Array<L> listeners = new Array<>();

    public BaseMapper(int index) {
        this.index = index;
    }

    public void addListener(L listener) {
        listeners.add(listener);
    }

    public void removeListener(L listener) {
        listeners.removeValue(listener, true);
    }
}
