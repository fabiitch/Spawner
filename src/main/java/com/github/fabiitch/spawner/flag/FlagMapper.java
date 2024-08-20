package com.github.fabiitch.spawner.flag;

import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntArray;
import com.github.fabiitch.spawner.archetype.criteria.FlagImpacted;
import com.github.fabiitch.spawner.listeners.FlagListener;
import com.github.fabiitch.spawner.utils.mappers.BaseMapper;

public class FlagMapper extends BaseMapper<FlagListener> implements FlagImpacted {
    private final Bits bits = new Bits();

    public FlagMapper(int index) {
        super(index);
    }

    public IntArray getAll(IntArray res) {
        int nextSetBit = bits.nextSetBit(0);
        while (nextSetBit != -1) {
            res.add(nextSetBit);
            nextSetBit = bits.nextSetBit(++nextSetBit);
        }
        return res;
    }

    public void setFlag(int entityId) {
        if (!hasFlag(entityId)) {
            bits.set(entityId);
            notifyAdd(entityId);
        }
    }

    void setUnsafe(int entityId) {
        bits.set(entityId);
    }

    public void removeFlag(int entityId) {
        if (hasFlag(entityId)) {
            bits.clear(entityId);
            notifyRemove(entityId);
        }
    }

    void removeUnsafe(int entityId) {
        bits.clear(entityId);
    }

    public boolean hasFlag(int entityId) {
        return bits.get(entityId);
    }

    private void notifyAdd(int entityId) {
        for (FlagListener internalListener : internalListeners)
            internalListener.onFlagAdd(entityId, index);

        for (FlagListener listener : listeners)
            listener.onFlagAdd(entityId, index);
    }

    private void notifyRemove(int entityId) {
        for (FlagListener internalListener : internalListeners)
            internalListener.onFlagRemove(entityId, index);

        for (FlagListener listener : listeners)
            listener.onFlagRemove(entityId, index);
    }

    void addInternalListener(FlagListener listener) {
        internalListeners.add(listener);
    }

    void removeInternalListener(FlagListener listener) {
        internalListeners.removeValue(listener, true);
    }

    @Override
    public boolean impactedByFlag(int flagIndex) {
        return flagIndex == this.index;
    }
}
