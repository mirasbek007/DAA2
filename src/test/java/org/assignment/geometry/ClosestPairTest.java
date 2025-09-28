package org.assignment.geometry;

import org.assignment.metrics.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {
    @Test
    public void smallBruteForce() {
        java.util.Random r = new java.util.Random(5);
        Point2D[] pts = new Point2D[200];
        for (int i=0;i<pts.length;i++) pts[i] = new Point2D(r.nextDouble()*100, r.nextDouble()*100);
        Metrics m = new Metrics();
        double fast = ClosestPair.closest(pts, m);
        double brute = ClosestPair.bruteForce(pts);
        assertEquals(brute, fast, 1e-9);
    }
}

