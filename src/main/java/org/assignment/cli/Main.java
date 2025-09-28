package org.assignment.cli;

import org.assignment.geometry.ClosestPair;
import org.assignment.geometry.Point2D;
import org.assignment.metrics.Metrics;
import org.assignment.sort.DeterministicSelect;
import org.assignment.sort.MergeSort;
import org.assignment.sort.QuickSort;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final Random RNG = new Random();

    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out, true);
        out.println("algo,n,time_ms,comparisons,allocations,maxDepth");
        int[] ns = new int[] {1000, 5000, 10000, 20000}; // change as needed
        for (int n : ns) {
            // MergeSort
            int[] arr = randArray(n);
            Metrics m = new Metrics();
            long t0 = System.nanoTime();
            MergeSort.sort(arr, m);
            long t1 = System.nanoTime();
            out.printf("mergesort,%d,%.3f,%d,%d,%d%n", n, (t1-t0)/1e6, m.comparisons.get(), m.allocations.get(), m.getMaxDepth());

            // QuickSort
            arr = randArray(n);
            m = new Metrics();
            t0 = System.nanoTime();
            QuickSort.sort(arr, m);
            t1 = System.nanoTime();
            out.printf("quicksort,%d,%.3f,%d,%d,%d%n", n, (t1-t0)/1e6, m.comparisons.get(), m.allocations.get(), m.getMaxDepth());

            // Deterministic Select: find median
            arr = randArray(n);
            m = new Metrics();
            t0 = System.nanoTime();
            int median = DeterministicSelect.select(Arrays.copyOf(arr, arr.length), n/2, m);
            t1 = System.nanoTime();
            out.printf("select,%d,%.3f,%d,%d,%d%n", n, (t1-t0)/1e6, m.comparisons.get(), m.allocations.get(), m.getMaxDepth());

            // Closest pair: random points in unit square
            Point2D[] pts = new Point2D[n];
            for (int i = 0; i < n; i++) pts[i] = new Point2D(RNG.nextDouble(), RNG.nextDouble());
            m = new Metrics();
            t0 = System.nanoTime();
            double cp = ClosestPair.closest(pts, m);
            t1 = System.nanoTime();
            out.printf("closest,%d,%.3f,%d,%d,%d%n", n, (t1-t0)/1e6, m.comparisons.get(), m.allocations.get(), m.getMaxDepth());
        }
    }

    private static int[] randArray(int n) {
        int[] a = new int[n];
        for (int i=0;i<n;i++) a[i] = RNG.nextInt();
        return a;
    }
}
