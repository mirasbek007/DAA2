package org.assignment.sort;

import org.assignment.metrics.Metrics;

public final class MergeSort {
    private static final int CUTOFF = 32; // insertion sort cutoff

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length < 2) return;
        int[] buf = new int[a.length];
        m.addAllocations(1); // buffer
        mergesort(a, buf, 0, a.length - 1, m);
    }

    private static void mergesort(int[] a, int[] buf, int lo, int hi, Metrics m) {
        m.enter();
        try {
            if (hi - lo + 1 <= CUTOFF) {
                insertion(a, lo, hi, m);
                return;
            }
            int mid = lo + ((hi - lo) >> 1);
            mergesort(a, buf, lo, mid, m);
            mergesort(a, buf, mid + 1, hi, m);
            if (a[mid] <= a[mid + 1]) { // already in order
                m.addComparisons(1);
                return;
            }
            merge(a, buf, lo, mid, hi, m);
        } finally {
            m.exit();
        }
    }

    private static void merge(int[] a, int[] buf, int lo, int mid, int hi, Metrics m) {
        // copy to buf
        System.arraycopy(a, lo, buf, lo, hi - lo + 1);
        m.addAllocations(hi - lo + 1);
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            m.incComparisons();
            if (buf[i] <= buf[j]) a[k++] = buf[i++]; else a[k++] = buf[j++];
        }
        while (i <= mid) a[k++] = buf[i++];
        while (j <= hi) a[k++] = buf[j++];
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
