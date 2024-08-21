package com.github.fabiitch.spawner.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;

public class BitsUtils {


    public static boolean equalsSafe(Bits bitsA, Bits bitsB) {
        if (bitsA != null) {
            bitsA.equals(bitsB);
        } else return bitsB == null;
        return true;
    }

    public static boolean equalsSafe(Array<Bits> bitsA, Array<Bits> bitsB) {
        if (bitsA != null) {
            bitsA.equals(bitsB);
        } else return bitsB == null;
        return true;
    }

    public static boolean nullEmpty(Bits bits) {
        return bits == null || bits.isEmpty();
    }

    public static boolean getNullSafe(Bits bits, int index) {
        return bits != null && bits.get(index);
    }

    public static boolean oneGet(Array<Bits> bitsArray, int index) {
        for (Bits bits : bitsArray)
            if (bits.get(index))
                return true;
        return false;
    }

    public static boolean oneIntersects(Array<Bits> bitsArray, Bits bits) {
        for (Bits bitsEntry : bitsArray)
            if (bitsEntry.intersects(bits))
                return true;
        return false;
    }

    public static boolean allIntersects(Array<Bits> bitsArray, Bits bits) {
        for (Bits bitsEntry : bitsArray)
            if (!bitsEntry.intersects(bits))
                return false;
        return true;
    }

    public static void orAll(Array<Bits> bitsArray, Bits bits) {
        for (Bits bitsEntry : bitsArray)
            bits.or(bitsEntry);
    }
}
