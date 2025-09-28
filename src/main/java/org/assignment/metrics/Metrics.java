package org.assignment.metrics;

import java.util.concurrent.atomic.AtomicLong;

public class Metrics {
    public final AtomicLong comparisons = new AtomicLong();
    public final AtomicLong allocations = new AtomicLong();
    private final ThreadLocal<Integer> depth = ThreadLocal.withInitial(() -> 0);
    private final AtomicLong maxDepth = new AtomicLong();

    public void incComparisons() { comparisons.incrementAndGet(); }
    public void addComparisons(long n) { comparisons.addAndGet(n); }
    public void incAllocations() { allocations.incrementAndGet(); }
    public void addAllocations(long n) { allocations.addAndGet(n); }

    public void enter() {
        int d = depth.get() + 1;
        depth.set(d);
        maxDepth.updateAndGet(curr -> Math.max(curr, d));
    }
    public void exit() {
        depth.set(Math.max(0, depth.get() - 1));
    }
    public long getMaxDepth() { return maxDepth.get(); }

    public void reset() {
        comparisons.set(0);
        allocations.set(0);
        depth.set(0);
        maxDepth.set(0);
    }
}

