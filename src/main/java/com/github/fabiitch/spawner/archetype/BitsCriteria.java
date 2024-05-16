package com.github.fabiitch.spawner.archetype;

import com.badlogic.gdx.utils.Bits;
import com.github.fabiitch.spawner.utils.BitsUtils;

class BitsCriteria {
    protected final Bits oneAccept;
    protected final Bits oneExclude;
    protected final Bits allAccept;
    protected final Bits allExclude;

    protected final Bits impacts = new Bits();

    public BitsCriteria(Bits oneAccept, Bits oneExclude, Bits allAccept, Bits allExclude) {
        this.oneAccept = oneAccept;
        this.oneExclude = oneExclude;
        this.allAccept = allAccept;
        this.allExclude = allExclude;

        if (oneAccept != null)
            impacts.or(oneAccept);
        if (oneExclude != null)
            impacts.or(oneExclude);
        if (allAccept != null)
            impacts.or(allAccept);
        if (allExclude != null)
            impacts.or(allExclude);
    }

    public boolean impacted(int index) {
        return impacts.get(index);
    }

    public boolean accept(Bits bits) {
        if (!BitsUtils.nullEmpty(allAccept) && !bits.containsAll(allAccept))
            return false;

        if (!BitsUtils.nullEmpty(allExclude) && bits.containsAll(allExclude))
            return false;

        if (!BitsUtils.nullEmpty(oneAccept) && !oneAccept.intersects(bits))
            return false;

        if (!BitsUtils.nullEmpty(oneExclude) && oneExclude.intersects(bits))
            return false;

        return true;
    }

    public boolean equals(BitsCriteria other) {
        return BitsUtils.equalsSafe(oneAccept, other.oneAccept)
                && BitsUtils.equalsSafe(oneExclude, other.oneExclude)
                && BitsUtils.equalsSafe(allAccept, other.allAccept)
                && BitsUtils.equalsSafe(allExclude, other.allExclude);
    }

}
