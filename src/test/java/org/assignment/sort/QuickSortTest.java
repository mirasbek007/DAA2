package org.assignment.sort;

import org.assignment.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test
    public void randomCorrectness() {
        int n = 2000;
        int[] a = new int[n];
        java.util.Random r = new java.util.Random(7);
        for (int i=0;i<n;i++) a[i] = r.nextInt();
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        assertArrayEquals(expected, a);
        // we could assert depth bound typical but randomized pivots mean bound is probabilistic
    }
}
