package org.assignment.util;

import java.util.Random;

public final class ArrayUtil {
    private static final Random RNG = new Random();

    public static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public static void swap(long[] a, int i, int j) {
        long t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = RNG.nextInt(i + 1);
            swap(a, i, j);
        }
    }
}

