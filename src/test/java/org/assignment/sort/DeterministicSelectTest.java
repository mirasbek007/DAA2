package org.assignment.sort;

import org.assignment.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectTest {
    @Test
    public void selectMedianRandom() {
        int n=1001;
        int[] a = new int[n];
        java.util.Random r = new java.util.Random(9);
        for (int i=0;i<n;i++) a[i] = r.nextInt(100000);
        int[] copy = Arrays.copyOf(a, a.length);
        Arrays.sort(copy);
        Metrics m = new Metrics();
        int med = DeterministicSelect.select(Arrays.copyOf(a, a.length), n/2, m);
        assertEquals(copy[n/2], med);
    }
}
