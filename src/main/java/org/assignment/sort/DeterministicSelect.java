package org.assignment.sort;

import org.assignment.metrics.Metrics;
import org.assignment.util.ArrayUtil;

public final class DeterministicSelect {
    // returns k-th smallest (0-based)
    public static int select(int[] a, int k, Metrics m) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of bounds");
        return selectInplace(a, 0, a.length - 1, k, m);
    }

    private static int selectInplace(int[] a, int lo, int hi, int k, Metrics m) {
        while (true) {
            m.enter();
            try {
                if (lo == hi) return a[lo];
                int pivot = medianOfMedians(a, lo, hi, m);
                int pivotPos = partitionAroundPivot(a, lo, hi, pivot, m);
                if (k == pivotPos) return a[k];
                else if (k < pivotPos) {
                    hi = pivotPos - 1;
                } else {
                    lo = pivotPos + 1;
                }
            } finally {
                m.exit();
            }
        }
    }

    private static int partitionAroundPivot(int[] a, int lo, int hi, int pivot, Metrics m) {
        int i = lo;
        int j = lo;
        for (; j <= hi; j++) {
            m.incComparisons();
            if (a[j] < pivot) {
                ArrayUtil.swap(a, i++, j);
            }
        }
        int storeLess = i;
        int storeEq = i;
        for (j = storeLess; j <= hi; j++) {
            m.incComparisons();
            if (a[j] == pivot) {
                ArrayUtil.swap(a, storeEq++, j);
            }
        }
        // place pivot region starting at storeLess; return any index in pivot region (choose storeLess)
        return storeLess;
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertionSort(a, lo, hi, m);
            return a[lo + n/2];
        }
        // partition into groups of 5, move medians to front
        int medCount = 0;
        for (int i = lo; i <= hi; i += 5) {
            int subHi = Math.min(i + 4, hi);
            insertionSort(a, i, subHi, m);
            int medianIdx = i + (subHi - i) / 2;
            ArrayUtil.swap(a, lo + medCount, medianIdx);
            medCount++;
        }
        // recursively select median of medians
        return selectInplace(a, lo, lo + medCount - 1, lo + medCount/2, m);
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics m) {
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

