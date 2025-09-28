package org.assignment.sort;

import org.assignment.metrics.Metrics;
import org.assignment.util.ArrayUtil;

import java.util.Random;

public final class QuickSort {
    private static final Random RNG = new Random();
    private static final int CUTOFF = 16;

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length < 2) return;
        // optional shuffle for adversarial cases (but we use randomized pivot)
        quicksort(a, 0, a.length - 1, m);
    }

    private static void quicksort(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) {
                insertion(a, lo, hi, m);
                return;
            }
            m.enter();
            int pivotIndex;
            try {
                pivotIndex = partition(a, lo, hi, m);
            } finally {
                m.exit();
            }
            int leftSize = pivotIndex - lo;
            int rightSize = hi - pivotIndex;
            // recurse on smaller side
            if (leftSize < rightSize) {
                quicksort(a, lo, pivotIndex - 1, m);
                lo = pivotIndex + 1; // tail recurse on the larger
            } else {
                quicksort(a, pivotIndex + 1, hi, m);
                hi = pivotIndex - 1;
            }
        }
    }

    private static int partition(int[] a, int lo, int hi, Metrics m) {
        int pivotIdx = lo + RNG.nextInt(hi - lo + 1);
        int pivot = a[pivotIdx];
        ArrayUtil.swap(a, pivotIdx, hi);
        int i = lo;
        for (int j = lo; j < hi; j++) {
            m.incComparisons();
            if (a[j] <= pivot) {
                ArrayUtil.swap(a, i++, j);
            }
        }
        ArrayUtil.swap(a, i, hi);
        return i;
    }

    private static void insertion(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.incComparisons();
                if (a[j] > key) { a[j+1] = a[j]; j--; }
                else break;
            }
            a[j+1] = key;
        }
    }
}

